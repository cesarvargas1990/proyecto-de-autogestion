import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { Observable } from 'rxjs';
import { Grilla } from '../user-management.model';

@Injectable({ providedIn: 'root' })
export class GrillaManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/departamentos');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(grilla: Grilla): Observable<Grilla> {
    return this.http.post<Grilla>(this.resourceUrl, grilla);
  }

  update(grilla: Grilla): Observable<Grilla> {
    return this.http.put<Grilla>(this.resourceUrl, grilla);
  }

  find(login: string): Observable<Grilla> {
    return this.http.get<Grilla>(`${this.resourceUrl}/${login}`);
  }

  query(req?: Pagination): Observable<HttpResponse<Grilla[]>> {
    const options = createRequestOption(req);
    return this.http.get<Grilla[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(login: string): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${login}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }

  findAll(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('/departamentos'));
  }
}
