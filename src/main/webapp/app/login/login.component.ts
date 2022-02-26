import { Component, ViewChild, OnInit, AfterViewInit, ElementRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoginModalComponent } from './modal/login-modal.component';
import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { TwoFAModalComponent } from './modal/twoFA-modal.component';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('username', { static: false })
  username!: ElementRef;

  authenticationError = false;
  statusError = false;
  loginForm = this.fb.group({
    username: [null, [Validators.required]],
    password: [null, [Validators.required]],
    rememberMe: [false],
  });

  constructor(
    private accountService: AccountService,
    private loginService: LoginService,
    private router: Router,
    private fb: FormBuilder,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    // if already authenticated then navigate to home page
    this.accountService.identity().subscribe(() => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['']);
      }
    });
  }

  ngAfterViewInit(): void {
    this.username.nativeElement.focus();
  }

  login(): void {
    this.loginService
      .login({
        username: this.loginForm.get('username')!.value,
        password: this.loginForm.get('password')!.value,
        rememberMe: this.loginForm.get('rememberMe')!.value,
      })
      .subscribe({
        next: sucessLogin => {
          this.authenticationError = false;
          this.statusError = false;
          /* eslint no-console: ["error", { allow: ["warn", "error"] }] */
          this.loginService.createToken(this.loginForm.get('username')!.value).subscribe();

          if (sucessLogin?.firstTime) {
            this.modalService.open(LoginModalComponent);
            this.modalService.open(TwoFAModalComponent);
            if (!this.router.getCurrentNavigation()) {
              this.router.navigate(['account/password']);
            }
          } else if (!sucessLogin?.firstTime) {
            this.modalService.open(TwoFAModalComponent);
            if (!this.router.getCurrentNavigation()) {
              this.router.navigate(['']);
            }
          }
        },
        error: errorLogin => {
          if (errorLogin.error.detail === 'Usuario Deshabilitado') {
            this.statusError = true;
            this.authenticationError = false;
          } else {
            this.authenticationError = true;
            this.statusError = false;
          }
        },
      });
  }
  showModal(): void {
    const modalRef = this.modalService.open(LoginModalComponent);
  }
}
