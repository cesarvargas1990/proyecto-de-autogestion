import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PasswordResetInitService } from './password-reset-init.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ResetModalComponent } from 'app/account/password-reset/modal/reset-modal.component';

@Component({
  selector: 'jhi-password-reset-init',
  templateUrl: './password-reset-init.component.html',
})
export class PasswordResetInitComponent implements AfterViewInit {
  @ViewChild('email', { static: false })
  email?: ElementRef;
  captcha = ''; // empty = not yet proven to be a human, anything else = human
  captchaValidator = true;

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
  }

  requestReset(): void {
    this.passwordResetInitService.save(this.resetRequestForm.get(['email'])!.value).subscribe({
      next: succesReset => {
        console.log(succesReset);
      },
      error: error => {
        console.log(error);
      },
    });
    this.modalService.open(ResetModalComponent);

    this.router.navigate(['account/reset/finish']);
  }
  resolved(captchaResponse: string): void {
    this.captcha = captchaResponse;
    this.captchaValidator = false;
  }
}
