import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ModalDismissReasons, NgbActiveModal, NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'jhi-twofa-modal',
  templateUrl: './twoFA-modal.component.html',
  providers: [NgbModalConfig, NgbModal],
})
export class TwoFAModalComponent implements OnInit, AfterViewInit {
  closeResult?: string;
  @ViewChild('content') myModal: any;

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
  ngAfterViewInit(): void {
    this.open(this.myModal);
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

  open(content: any) {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
      result => {
        this.closeResult = `Closed with: ${result}`;
      },
      reason => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      }
    );
  }
  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }
}
