import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransaccionesNomina } from '../transacciones-nomina.model';
import { TransaccionesNominaService } from '../service/transacciones-nomina.service';
import { TransaccionesNominaDeleteDialogComponent } from '../delete/transacciones-nomina-delete-dialog.component';

@Component({
  selector: 'jhi-transacciones-nomina',
  templateUrl: './transacciones-nomina.component.html',
})
export class TransaccionesNominaComponent implements OnInit {
  transaccionesNominas?: ITransaccionesNomina[];
  isLoading = false;

  constructor(protected transaccionesNominaService: TransaccionesNominaService, protected modalService: NgbModal) {}

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

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITransaccionesNomina): number {
    return item.id!;
  }

  delete(transaccionesNomina: ITransaccionesNomina): void {
    const modalRef = this.modalService.open(TransaccionesNominaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transaccionesNomina = transaccionesNomina;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
