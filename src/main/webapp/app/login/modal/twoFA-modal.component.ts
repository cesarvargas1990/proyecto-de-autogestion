import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'jhi-twofa-modal',
  templateUrl: './twoFA-modal.component.html',
  providers: [NgbModalConfig, NgbModal],
})
export class TwoFAModalComponent implements OnInit {
  @ViewChild('content')
  content?: TwoFAModalComponent;

  tokenValidado = false;
  tokenError = false;
  pruebaesta = false;
  itsTwoFAON = true;
  account$?: Observable<Account | null>;

  twoFactorForm = this.fb.group({
    token: [null, [Validators.required]],
  });

  constructor(
    private activeModal: NgbActiveModal,
    private loginService: LoginService,
    private fb: FormBuilder,
    private accountService: AccountService,
    private modalService: NgbModal,

    config: NgbModalConfig
  ) {
    config.backdrop = 'static';
    config.keyboard = false;
  }
  ngOnInit(): void {
    this.account$ = this.accountService.identity();
  }

  dismiss(): void {
    this.activeModal.dismiss();
  }

  verificar(): void {
    this.loginService.validarToken(this.twoFactorForm.get('token')!.value).subscribe({
      next: validatedToken => {
        this.tokenValidado = true;
        this.tokenError = false;
        this.pruebaesta = false;

        /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
        this.account$?.subscribe({
          next: personaLogeada => {
            this.loginService.validarPrimerLogin(personaLogeada!.login).subscribe();
          },
        });
      },

      error: wrongToken => {
        this.tokenValidado = false;
        this.tokenError = true;
      },
    });
  }

  open(content: any): void {
    this.modalService.open(content);
  }
}
