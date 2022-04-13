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
  private resourceUrl2 = this.applicationConfigService.getEndpointFor('api/users');
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
    return this.http.get<string[]>(`${this.resourceUrl2}/departamentos/${id}`);
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

  getIdMultiplesDepartamentos(departamentoName: string): Observable<number[]> {
    return this.http.get<number[]>(
      this.applicationConfigService.getEndpointFor('api/findIdByMultiplesName?departamentoName=' + `${departamentoName}`)
    );
  }

  getIdMultipleDepartamentos(departamentoName: string): Observable<number> {
    return this.http.get<number>(
      this.applicationConfigService.getEndpointFor('api/findIdByMultipleName?departamentoName=' + `${departamentoName}`)
    );
  }

  findDepartamentosIDByMunicipioName(municipioName: string): Observable<string> {
    return this.http.get<string>(
      this.applicationConfigService.getEndpointFor('api/findDepartamentosIDByMunicipioName?municipioName=' + `${municipioName}`)
    );
  }

  /* eslint-disable */

  getMultiplesMunicipios(fkDepartmanento: number[]): Observable<string[]> {
    return this.http.get<string[]>(
      this.applicationConfigService.getEndpointFor('api/getMultiplesMunicipios?fkDepartmanento=' + `${fkDepartmanento}`)
    );
  }
  /* eslint-enable */

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

  MakeinsertUDM(udmmodel: udmModel): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/admin/insertUDM'), udmmodel);
  }

  getDepartamentosName(IdUser: number): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/findDepartamentosNameByID?IdUser=' + `${IdUser}`));
  }

  findMunicipiosNameByID(IdUser: number): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/findMunicipiosNameByID?IdUser=' + `${IdUser}`));
  }
  /* eslint-disable */

  findMunicipiosNameByIDAndDepartamento(IdUser: number, departamentoId: number[]): Observable<string[]> {
    return this.http.get<string[]>(
      this.applicationConfigService.getEndpointFor(
        'api/findMunicipiosNameByIDAndDepartamento?IdUser=' + `${IdUser}` + '&departamentoId=' + `${departamentoId}`
      )
    );
  }

  /* eslint-enable */

  //NO FUNCIONA
  findProgramaName(IdUser: number): Observable<string> {
    return this.http.get<string>(this.applicationConfigService.getEndpointFor('api/findProgramaName?IdUser=' + `${IdUser}`));
  }

  findProgramasName(IdUser: number, programaName: string): Observable<string> {
    return this.http.get<string>(
      this.applicationConfigService.getEndpointFor('api/findProgramasName?IdUser=' + `${IdUser}` + '&?programaName=' + `${programaName}`)
    );
  }

  findConvenioID(IdUser: number): Observable<string> {
    return this.http.get<string>(this.applicationConfigService.getEndpointFor('api/findConvenioID?IdUser=' + `${IdUser}`));
  }

  //DUDOSOS

  getIdDepartamentos(departamentoName: string): Observable<number> {
    return this.http.get<number>(
      this.applicationConfigService.getEndpointFor('api/getIdDepartamentos?departamentoName=' + `${departamentoName}`)
    );
  }

  getDepartamentosIDByMunicipiosName(municipioName: string): Observable<number> {
    return this.http.get<number>(
      this.applicationConfigService.getEndpointFor('api/getDepartamentosIDByMunicipiosName?municipioName=' + `${municipioName}`)
    );
  }
  getNameConvenio(convenioId: number): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/findNameByIdConvenio?convenioId=' + `${convenioId}`));
  }

  getNamePrograma(programaId: number): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/findNameByIdPrograma?programaId=' + `${programaId}`));
  }

  getNameDepartamento(departamentoId: number): Observable<string[]> {
    return this.http.get<string[]>(
      this.applicationConfigService.getEndpointFor('api/findNameByIdDepartamento?departamentoId=' + `${departamentoId}`)
    );
  }

  getNameMunicipio(municipioId: number): Observable<string[]> {
    return this.http.get<string[]>(
      this.applicationConfigService.getEndpointFor('api/findNameByIdMunicipio?municipioId=' + `${municipioId}`)
    );
  }

  getBooleanSearchDB(login: string, documentType: string): Observable<true> {
    return this.http.get<true>(
      this.applicationConfigService.getEndpointFor('api/searchInDB?login=' + `${login}` + '&?documentType=' + `${documentType}`)
    );
  }

  getMunicipios(fkDepartmanento: number): Observable<string[]> {
    return this.http.get<string[]>(
      this.applicationConfigService.getEndpointFor('api/getMunicipios?fkDepartmanento=' + `${fkDepartmanento}`)
    );
  }

  // getMultiplesDepartamentosIDByMunicipiosName(municipioName: string[]): Observable<string[]> {
  //   return this.http.get<string[]>(
  //     this.applicationConfigService.getEndpointFor('api/findMultiplesDepartamentosIDByMunicipiosName?municipioName=' + `${municipioName}`)
  //   );
  // }
}
