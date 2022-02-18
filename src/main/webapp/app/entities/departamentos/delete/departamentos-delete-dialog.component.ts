import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDepartamentos } from '../departamentos.model';
import { DepartamentosService } from '../service/departamentos.service';

@Component({
  templateUrl: './departamentos-delete-dialog.component.html',
})
export class DepartamentosDeleteDialogComponent {
  departamentos?: IDepartamentos;

  constructor(protected departamentosService: DepartamentosService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.departamentosService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
