import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';
import { Login } from './login.model';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class LoginService {
  constructor(
    private applicationConfigService: ApplicationConfigService,
    private http: HttpClient,
    private accountService: AccountService,
    private authServerProvider: AuthServerProvider
  ) {}

  login(credentials: Login): Observable<Account | null> {
    return this.authServerProvider.login(credentials).pipe(mergeMap(() => this.accountService.identity(true)));
  }

  logout(): void {
    this.authServerProvider.logout().subscribe({ complete: () => this.accountService.authenticate(null) });
  }

  createToken(document: string): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/account/generateToken'), document);
  }

  validarToken(token: string): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/account/validateToken'), token);
  }

  validarPrimerLogin(login: string): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/account/userLoged'), login);
  }
}
