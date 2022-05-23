import { HttpResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserManagementService } from 'app/admin/user-management/service/user-management.service';
import { SITE_KEY_CAPTCHA, TIRILLA_URI } from 'app/app.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { IConvenio } from 'app/entities/convenio/convenio.model';
import { ConvenioService } from 'app/entities/convenio/service/convenio.service';
import { IDepartamentos } from 'app/entities/departamentos/departamentos.model';
import { DepartamentosService } from 'app/entities/departamentos/service/departamentos.service';
import { IMunicipio } from 'app/entities/municipio/municipio.model';
import { MunicipioService } from 'app/entities/municipio/service/municipio.service';
import { IProgramas } from 'app/entities/programas/programas.model';
import { ProgramasService } from 'app/entities/programas/service/programas.service';
// import { TransaccionesNominaDeleteDialogComponent } from 'app/entities/transacciones-nomina/delete/transacciones-nomina-delete-dialog.component';
import { TransaccionesNominaService } from 'app/entities/transacciones-nomina/service/transacciones-nomina.service';
import { ITransaccionesNomina } from 'app/entities/transacciones-nomina/transacciones-nomina.model';
import { elementAt, Observable, Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'jhi-consulta-estado-giro',
  templateUrl: './consulta-estado-giro.component.html',
  styleUrls: ['./consulta-estado-giro.component.scss'],
})
export class ConsultaEstadoGiroComponent implements OnInit, AfterViewInit {
  /* eslint-disable */
  department!: IDepartamentos;
  departmentOfUser!: string[];
  municipio!: IMunicipio;
  convenio!: IConvenio;
  programas!: IProgramas;
  transaccionesNominas!: ITransaccionesNomina[];
  transaccionesNominas2!: ITransaccionesNomina[];
  idUserLogin!: number;
  account$?: Observable<Account | null>;
  nameDept?: string;
  isLoading = false;
  programaUserLogged!: number;
  nitprogramaUserLogged!: string;
  isAdmin!: boolean;
  blob!: Blob;
  errorTirilla = false;
  pinNomina!: string;
  documentNumber!: string | undefined;

  public formSearch!: FormGroup;

  account: Account | null = null;

  private readonly destroy$ = new Subject<void>();

  constructor(
    private formBuilder: FormBuilder,
    protected transaccionesNominaService: TransaccionesNominaService,
    protected departamentosService: DepartamentosService,
    protected municipioService: MunicipioService,
    protected convenioService: ConvenioService,
    protected programasService: ProgramasService,
    protected userService: UserManagementService,
    protected modalService: NgbModal,
    private accountService: AccountService,
    private router: Router
  ) {}

  ngAfterViewInit(): void {
    this.getDataUser();
  }

  codDaneDepto(codDane: any): IDepartamentos {
    this.departamentosService.findByCodDane(codDane).subscribe({
      next: (res: HttpResponse<IDepartamentos>) => {
        this.isLoading = false;
        this.department = res.body ?? this.department;
      },
      error: () => {
        this.isLoading = false;
      },
    });
    return this.department;
  }

  // idDepto(id: any): IDepartamentos {
  //   this.departamentosService.find(id).subscribe({
  //     next: (res: HttpResponse<IDepartamentos>) => {
  //       this.isLoading = false;
  //       this.departmentOfUser = res.body ?? this.departmentOfUser;
  //     },
  //     error: () => {
  //       this.isLoading = false;
  //     },
  //   });
  //   return this.departmentOfUser;
  // }

  codDaneMunicipio(codDane: any): IMunicipio {
    this.municipioService.findByCodDane(codDane).subscribe({
      next: (res: HttpResponse<IMunicipio>) => {
        this.isLoading = false;
        this.municipio = res.body ?? this.department;
        /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
        // console.log(codDane);
      },
      error: () => {
        this.isLoading = false;
      },
    });
    return this.municipio;
  }

  nitConvenio(nit: any): IConvenio {
    this.convenioService.findByNit(nit).subscribe({
      next: (res: HttpResponse<IConvenio>) => {
        this.isLoading = false;
        this.convenio = res.body ?? this.department;
        /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
        // console.log(codDane);
      },
      error: () => {
        this.isLoading = false;
      },
    });
    return this.convenio;
  }

  nitProgramas(nit: any): IProgramas {
    this.programasService.findByNit(nit).subscribe({
      next: (res: HttpResponse<IProgramas>) => {
        this.isLoading = false;
        this.programas = res.body ?? this.department;
        /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
        // console.log(codDane);
      },
      error: () => {
        this.isLoading = false;
      },
    });
    return this.programas;
  }
  // getNitConvenioUser(): string{
  //   return this.convenioService.find(this)
  // }

  getDataUser(): void {
    this.account$?.subscribe({
      next: user => {
        this.idUserLogin = user?.id ?? 0;
        console.log(user);
        this.programaUserLogged = user?.programa ?? 0;
        this.programasService.find(this.programaUserLogged).subscribe({
          next: programa => {
            this.nitprogramaUserLogged = programa?.body?.identificacion ?? 'invalido';
          },
        });
        this.userService.findDepartmentById(this.idUserLogin).subscribe({
          next: (res: string[]) => {
            this.departmentOfUser = res;

            /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
            // console.log(this.departmentOfUser);

            /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
            // console.log(this.idUserLogin);
          },
        });
      },
    });
  }

  send(): any {
    console.log(this.nitprogramaUserLogged);
    let lokesea: ITransaccionesNomina[] = [];

    for (let index = 0; index < this.departmentOfUser.length; index++) {
      this.transaccionesNominaService
        .findByDocument(
          this.formSearch.value.numberDocument,
          this.formSearch.value.typeDocument,
          this.departmentOfUser[index],
          this.nitprogramaUserLogged,
          this.formSearch.value.idNomina
        )
        .subscribe({
          next: (res: HttpResponse<ITransaccionesNomina[]>) => {
            this.isLoading = false;
            this.transaccionesNominas2 = res.body ?? [];

            lokesea = lokesea.concat(this.transaccionesNominas2);
            this.documentNumber = this.formSearch.value.numberDocument;

            setTimeout(() => {
              this.transaccionesNominas = lokesea;
              this.pinNomina = this.transaccionesNominas[0].pinPago ?? 'nulo';
            }, 300);
          },
          error: () => {
            this.isLoading = false;
          },
        });
    }
  }
  trackId(index: number, item: ITransaccionesNomina): number {
    return item.id!;
  }

  ngOnInit(): void {
    console.log('oninit');
    this.account$ = this.accountService.identity();

    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
    this.formSearch = this.formBuilder.group({
      typeDocument: [''],
      numberDocument: [''],
      idNomina: [''],
    });
  }

  //tirillas

  displayTirilla(index: number): void {
    this.pinNomina = this.transaccionesNominas[index].pinPago ?? 'nulo';
    this.documentNumber != this.transaccionesNominas[index].numeroDocumentoBenef?.toString;

    this.transaccionesNominaService.findTirillas(this.pinNomina, this.documentNumber!).subscribe({
      next: (data: Blob) => {
        var file = new Blob([data], { type: 'application/pdf' });
        var fileURL = URL.createObjectURL(file);
        console.log('la url es: ', fileURL);
        // if you want to open PDF in new tab
        window.open(fileURL);
        var a = document.createElement('a');
        a.href = fileURL;
        a.target = '_blank';

        document.body.appendChild(a);
        a.click();
      },
      error: error => {
        if (error.status === 412) {
          this.errorTirilla = true;
          setTimeout(() => {
            this.errorTirilla = false;
          }, 3000);
        }
      },
    });
  }
  downloadTirilla(index: number): void {
    this.pinNomina = this.transaccionesNominas[index].pinPago ?? 'nulo';
    this.documentNumber != this.transaccionesNominas[index].numeroDocumentoBenef?.toString;

    this.transaccionesNominaService.findTirillas(this.pinNomina, this.documentNumber!).subscribe({
      next: data => {
        this.blob = new Blob([data], { type: 'application/pdf' });

        var downloadURL = window.URL.createObjectURL(data);
        var link = document.createElement('a');
        link.href = downloadURL;
        link.download = this.pinNomina + '-' + this.documentNumber + '.pdf';
        link.click();
      },

      error: error => {
        if (error.status === 412) {
          this.errorTirilla = true;
          setTimeout(() => {
            this.errorTirilla = false;
          }, 3000);
        }
      },
    });

    /* eslint-enable */
  }
}
