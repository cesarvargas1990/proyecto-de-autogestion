import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { LoginService } from 'app/login/login.service';
@Component({
  selector: 'jhi-twoFa-modal',
  templateUrl: './twoFA-modal.component.html',
})
export class TwoFAModalComponent {
  twoFactorForm = this.fb.group({
    token: [null, [Validators.required]],
  });
  tokenValidado = false;
  tokenError = false;
  constructor(private activeModal: NgbActiveModal, private loginService: LoginService, private fb: FormBuilder) {}

  dismiss(): void {
    this.activeModal.dismiss();
  }

  verificar(): void {
    this.loginService.validarToken(this.twoFactorForm.get('token')!.value).subscribe({
      next: validatedToken => {
        this.tokenValidado = true;
        this.tokenError = false;
      },

      error: wrongToken => {
        this.tokenValidado = false;
        this.tokenError = true;
      },
    });
  }
}
