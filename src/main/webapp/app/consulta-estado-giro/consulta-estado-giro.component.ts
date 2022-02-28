import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
// import { TransaccionesNominaDeleteDialogComponent } from 'app/entities/transacciones-nomina/delete/transacciones-nomina-delete-dialog.component';
import { TransaccionesNominaService } from 'app/entities/transacciones-nomina/service/transacciones-nomina.service';
import { ITransaccionesNomina } from 'app/entities/transacciones-nomina/transacciones-nomina.model';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'jhi-consulta-estado-giro',
  templateUrl: './consulta-estado-giro.component.html',
  styleUrls: ['./consulta-estado-giro.component.scss'],
})
export class ConsultaEstadoGiroComponent implements OnInit {
  transaccionesNominas2?: ITransaccionesNomina;
  transaccionesNominas?: ITransaccionesNomina[];
  isLoading = false;

  public formSearch!: FormGroup;

  account: Account | null = null;

  private readonly destroy$ = new Subject<void>();

  constructor(
    private formBuilder: FormBuilder,
    protected transaccionesNominaService: TransaccionesNominaService,
    protected modalService: NgbModal,
    private accountService: AccountService,
    private router: Router
  ) {}

  loadAll(): void {
    this.isLoading = true;

    this.transaccionesNominaService.query().subscribe({
      next: (res: HttpResponse<ITransaccionesNomina[]>) => {
        this.isLoading = false;
        this.transaccionesNominas = res.body ?? [];
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
        this.transaccionesNominas = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
    // /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
    //     console.log(this.formSearch.value);
  }

  trackId(index: number, item: ITransaccionesNomina): number {
    return item.id!;
  }

  ngOnInit(): void {
    // this.loadAll();
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
