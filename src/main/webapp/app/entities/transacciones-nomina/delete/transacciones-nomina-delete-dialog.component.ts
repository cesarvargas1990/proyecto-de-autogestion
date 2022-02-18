import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransaccionesNomina } from '../transacciones-nomina.model';
import { TransaccionesNominaService } from '../service/transacciones-nomina.service';

@Component({
  templateUrl: './transacciones-nomina-delete-dialog.component.html',
})
export class TransaccionesNominaDeleteDialogComponent {
  transaccionesNomina?: ITransaccionesNomina;

  constructor(protected transaccionesNominaService: TransaccionesNominaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transaccionesNominaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
