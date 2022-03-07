import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IUser } from '../user-management.model';
import { udmModel } from '../user-management.model';

import { User } from '../user-management.model';

@Injectable({ providedIn: 'root' })
export class UserManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/admin/users');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(this.resourceUrl, user);
  }

  update(user: User): Observable<User> {
    return this.http.put<User>(this.resourceUrl, user);
  }

  find(login: string): Observable<User> {
    return this.http.get<User>(`${this.resourceUrl}/${login}`);
  }

  findDepartmentById(id: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.resourceUrl}/departamentos/${id}`);
  }

  query(req?: Pagination): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(login: string): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${login}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }

  getDepartamentos(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/getDepartamentos'));
  }

  getIdDepartamentos(departamentoName: string): Observable<number> {
    return this.http.get<number>(
      this.applicationConfigService.getEndpointFor('api/getIdDepartamentos?departamentoName=' + `${departamentoName}`)
    );
  }

  getMunicipios(fkDepartmanento: number): Observable<string[]> {
    return this.http.get<string[]>(
      this.applicationConfigService.getEndpointFor('api/getMunicipios?fkDepartmanento=' + `${fkDepartmanento}`)
    );
  }

  getIdMunicipios(municipioName: string): Observable<number> {
    return this.http.get<number>(this.applicationConfigService.getEndpointFor('api/getIdMunicipios?municipioName=' + `${municipioName}`));
  }

  getConvenios(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/getConvenio'));
  }

  getIdConvenios(convenioName: string): Observable<number> {
    return this.http.get<number>(this.applicationConfigService.getEndpointFor('api/getIdConvenio?convenioName=' + `${convenioName}`));
  }

  getProgramas(fkConvenio: number): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/getProgramas?fkConvenio=' + `${fkConvenio}`));
  }

  getIdProgramas(programaName: string): Observable<number> {
    return this.http.get<number>(this.applicationConfigService.getEndpointFor('api/getIdProgramas?programaName=' + `${programaName}`));
  }

  getBooleanSearchDB(login: string, documentType: string): Observable<true> {
    return this.http.get<true>(
      this.applicationConfigService.getEndpointFor('api/searchInDB?login=' + `${login}` + '&?documentType=' + `${documentType}`)
    );
  }

  MakeinsertUDM(udmmodel: udmModel): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/admin/insertUDM'), udmmodel);
  }
}
