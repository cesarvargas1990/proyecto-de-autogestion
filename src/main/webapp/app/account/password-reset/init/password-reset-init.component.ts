import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PasswordResetInitService } from './password-reset-init.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ResetModalComponent } from 'app/account/password-reset/modal/reset-modal.component';
import { SITE_KEY_CAPTCHA } from 'app/app.constants';

@Component({
  selector: 'jhi-password-reset-init',
  templateUrl: './password-reset-init.component.html',
})
export class PasswordResetInitComponent implements AfterViewInit {
  @ViewChild('email', { static: false })
  email?: ElementRef;
  captcha = ''; // empty = not yet proven to be a human, anything else = human
  captchaValidator = true;
  emailEnmascarado = '';
  emailEnviado = false;
  stringSeguro: any;
  siteKeyCaptcha!: string;
  success = false;
  resetRequestForm = this.fb.group({
    email: ['', [Validators.required, Validators.maxLength(20), Validators.pattern('^[A-Z0-9-]*$')]],
  });

  constructor(
    private passwordResetInitService: PasswordResetInitService,
    private fb: FormBuilder,
    private router: Router,
    private modalService: NgbModal
  ) {}

  ngAfterViewInit(): void {
    if (this.email) {
      this.email.nativeElement.focus();
    }
    this.siteKeyCaptcha = SITE_KEY_CAPTCHA;
  }

  requestReset(): void {
    this.passwordResetInitService.save(this.resetRequestForm.get(['email'])!.value).subscribe(loquellega => {
      /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
      //this.emailEnmascarado=loquellega.email;

      this.emailEnmascarado = this.enmascararMail(loquellega.email);
      this.emailEnviado = true;
      setTimeout(() => {
        this.router.navigate(['account/reset/finish']);
      }, 3000);
    });
  }
  resolved(captchaResponse: string): void {
    this.captcha = captchaResponse;
    this.captchaValidator = false;
  }

  enmascararMail(email: any): string {
    if (email) {
      email = email.split('');
      const finalArray: any = [];
      const length = email.indexOf('@');
      email.forEach((item: any, pos: any) => {
        pos >= 2 && pos <= length - 1 ? finalArray.push('*') : finalArray.push(email[pos]);
      });
      this.stringSeguro = finalArray.join('');

      return String(this.stringSeguro);
    } else {
      return '';
    }
  }
}
