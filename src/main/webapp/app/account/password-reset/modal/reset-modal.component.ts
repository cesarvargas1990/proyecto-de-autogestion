import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-reset-modal',
  templateUrl: './reset-modal.component.html',
})
export class ResetModalComponent {
  emailEnmascarado = '';
  constructor(private activeModal: NgbActiveModal) {}

  dismiss(): void {
    this.activeModal.dismiss();
  }
}
