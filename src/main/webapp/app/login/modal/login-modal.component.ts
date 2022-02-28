import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-login-modal',
  templateUrl: './login-modal.component.html',
})
export class LoginModalComponent {
  constructor(private activeModal: NgbActiveModal) {}

  dismiss(): void {
    this.activeModal.dismiss();
  }
}
