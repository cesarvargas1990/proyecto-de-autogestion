import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { LOGIN_ROUTE } from './login.route';
import { LoginComponent } from './login.component';
import { LoginModalComponent } from './modal/login-modal.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([LOGIN_ROUTE])],
  declarations: [LoginComponent, LoginModalComponent],
  entryComponents: [LoginModalComponent],
})
export class LoginModule {}
