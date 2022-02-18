import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProgramas } from '../programas.model';
import { ProgramasService } from '../service/programas.service';

@Component({
  templateUrl: './programas-delete-dialog.component.html',
})
export class ProgramasDeleteDialogComponent {
  programas?: IProgramas;

  constructor(protected programasService: ProgramasService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.programasService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
