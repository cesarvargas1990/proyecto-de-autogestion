import { Component, OnInit, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { PasswordResetFinishService } from './password-reset-finish.service';

@Component({
  selector: 'jhi-password-reset-finish',
  templateUrl: './password-reset-finish.component.html',
})
export class PasswordResetFinishComponent implements OnInit, AfterViewInit {
  @ViewChild('newPassword', { static: false })
  newPassword?: ElementRef;

  initialized = false;
  doNotMatch = false;
  error = false;
  key = '';
  success = false;
  bodyParam = false;
  passwordForm = this.fb.group({
    newPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    inputOTP: ['', Validators.required],
  });

  constructor(private passwordResetFinishService: PasswordResetFinishService, private route: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['key']) {
        this.key = params['key'];
        if (this.key.length > 5) {
          this.bodyParam = true;
        }
      }
    });

    this.initialized = true;
  }

  ngAfterViewInit(): void {
    if (this.newPassword) {
      this.newPassword.nativeElement.focus();
    }
  }

  finishReset(): void {
    this.doNotMatch = false;
    this.error = false;

    const newPassword = this.passwordForm.get(['newPassword'])!.value;
    const confirmPassword = this.passwordForm.get(['confirmPassword'])!.value;
    const key2 = this.passwordForm.get(['inputOTP'])!.value;
    if (newPassword !== confirmPassword) {
      this.doNotMatch = true;
    } else {
      this.passwordResetFinishService.save(key2, newPassword).subscribe({
        next: () => (this.success = true),
        error: () => (this.error = true),
      });
    }
  }
}
