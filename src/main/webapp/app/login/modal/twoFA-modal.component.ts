import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';

import { FormBuilder, Validators } from '@angular/forms';
import { ModalDismissReasons, NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { LoginService } from 'app/login/login.service';
import { LoginModalComponent } from './login-modal.component';
import { AccountService } from 'app/core/auth/account.service';

import { Account } from 'app/core/auth/account.model';

import { Router } from '@angular/router';

import { Observable } from 'rxjs';

@Component({
  selector: 'jhi-twofa-modal',

  templateUrl: './twoFA-modal.component.html',

  providers: [NgbModalConfig, NgbModal],
})
export class TwoFAModalComponent implements OnInit, AfterViewInit {
  closeResult?: string;
  @ViewChild('content') myModal: string | undefined;

  tokenValidado = false;

  tokenError = false;

  pruebaesta = true;

  itsTwoFAON = true;

  account$?: Observable<Account | null>;

  twoFactorForm = this.fb.group({
    token: [null, [Validators.required]],
  });

  constructor(
    private loginService: LoginService,

    private fb: FormBuilder,

    private accountService: AccountService,

    private modalService: NgbModal,

    private router: Router,

    config: NgbModalConfig
  ) {
    config.backdrop = 'static';

    config.keyboard = false;
    config.centered = true;
  }

  ngAfterViewInit(): void {
    this.open(this.myModal);
  }

  ngOnInit(): void {
    this.account$ = this.accountService.identity();
  }

  dismiss(): void {
    this.modalService.dismissAll();
  }

  verificar(): void {
    this.loginService.loginfinish(this.twoFactorForm.get('token')!.value).subscribe({
      next: validatedToken => {
        this.tokenValidado = true;
        this.tokenError = false;
        this.pruebaesta = false;
        setTimeout(() => {
          this.modalService.dismissAll();
          if (validatedToken?.firstTime) {
            this.loginService.validarPrimerLogin(validatedToken.login).subscribe(() => {
              this.modalService.open(LoginModalComponent);
              this.router.navigate(['/account/password']);
            });
          }
        }, 800);
      },

      error: wrongToken => {
        this.tokenValidado = false;

        this.tokenError = true;
      },
    });
  }

  open(content: string | undefined): void {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(result => {
      //this.closeResult = `Closed with: ${result}`;
    });
  }
}
