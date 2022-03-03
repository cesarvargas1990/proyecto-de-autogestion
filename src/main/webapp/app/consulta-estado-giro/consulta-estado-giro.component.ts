import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
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
export class ConsultaEstadoGiroComponent implements OnInit {
  department!: IDepartamentos;
  municipio!: IMunicipio;
  convenio!: IConvenio;
  programas!: IProgramas;
  transaccionesNominas!: ITransaccionesNomina[];
  transaccionesNominas2!: ITransaccionesNomina[];
  nameDept?: string;
  isLoading = false;

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
    protected modalService: NgbModal,
    private accountService: AccountService,
    private router: Router
  ) {}

  codDaneDepto(codDane: any): IDepartamentos {
    this.departamentosService.findByCodDane(codDane).subscribe({
      next: (res: HttpResponse<IDepartamentos>) => {
        this.isLoading = false;
        this.department = res.body ?? this.department;
        /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
        // console.log(codDane);
      },
      error: () => {
        this.isLoading = false;
      },
    });
    return this.department;
  }

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

  loadAll(): void {
    this.isLoading = true;

    this.transaccionesNominaService.query().subscribe({
      next: (res: HttpResponse<ITransaccionesNomina[]>) => {
        this.isLoading = false;
        this.transaccionesNominas2 = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  send(): any {
    this.transaccionesNominaService.findByDocument(this.formSearch.value.numberDocument, this.formSearch.value.typeDocument).subscribe({
      next: (res: HttpResponse<ITransaccionesNomina[]>) => {
        this.isLoading = false;
        this.transaccionesNominas2 = res.body ?? [];

        for (let index = 0; index < this.transaccionesNominas2.length; index++) {
          if (
            this.transaccionesNominas2[index].fKDepartamentoDePago !== null &&
            this.transaccionesNominas2[index].fKDepartamentoDePago !== undefined
          ) {
            this.codDaneDepto(this.transaccionesNominas2[index].fKDepartamentoDePago);
            this.codDaneMunicipio(this.transaccionesNominas2[index].fKMunicipioDePago);

            setTimeout(() => {
              this.transaccionesNominas2[index].fKDepartamentoDePago = this.department.name;
              this.transaccionesNominas2[index].fKMunicipioDePago = this.municipio.name;
            }, 100);
          }
          if (this.transaccionesNominas2[index].fKIdConvenio !== null && this.transaccionesNominas2[index].fKIdPrograma !== null) {
            this.nitConvenio(this.transaccionesNominas2[index].fKIdConvenio);
            this.nitProgramas(this.transaccionesNominas2[index].fKIdPrograma);

            setTimeout(() => {
              this.transaccionesNominas2[index].fKIdConvenio = this.convenio.name;
              this.transaccionesNominas2[index].fKIdPrograma = this.programas.name;
            }, 100);
          }
        }
        setTimeout(() => {
          this.transaccionesNominas = this.transaccionesNominas2;
        }, 200);
      },
      error: () => {
        this.isLoading = false;
      },
    });
    // /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
    //     console.log(this.transaccionesNominas);
  }

  trackId(index: number, item: ITransaccionesNomina): number {
    return item.id!;
  }

  ngOnInit(): void {
    this.loadAll();
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));

    this.formSearch = this.formBuilder.group({
      typeDocument: [''],
      numberDocument: [''],
    });
  }
}
