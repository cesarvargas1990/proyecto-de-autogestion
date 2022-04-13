import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDepartamentos, getDepartamentosIdentifier } from '../departamentos.model';

export type EntityResponseType = HttpResponse<IDepartamentos>;
export type EntityArrayResponseType = HttpResponse<IDepartamentos[]>;

@Injectable({ providedIn: 'root' })
export class DepartamentosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/departamentos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(departamentos: IDepartamentos): Observable<EntityResponseType> {
    return this.http.post<IDepartamentos>(this.resourceUrl, departamentos, { observe: 'response' });
  }

  update(departamentos: IDepartamentos): Observable<EntityResponseType> {
    return this.http.put<IDepartamentos>(`${this.resourceUrl}/${getDepartamentosIdentifier(departamentos) as number}`, departamentos, {
      observe: 'response',
    });
  }

  partialUpdate(departamentos: IDepartamentos): Observable<EntityResponseType> {
    return this.http.patch<IDepartamentos>(`${this.resourceUrl}/${getDepartamentosIdentifier(departamentos) as number}`, departamentos, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDepartamentos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByCodDane(codDane: number): Observable<EntityResponseType> {
    return this.http.get<IDepartamentos>(`${this.resourceUrl}/codDane/${codDane}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepartamentos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDepartamentosToCollectionIfMissing(
    departamentosCollection: IDepartamentos[],
    ...departamentosToCheck: (IDepartamentos | null | undefined)[]
  ): IDepartamentos[] {
    const departamentos: IDepartamentos[] = departamentosToCheck.filter(isPresent);
    if (departamentos.length > 0) {
      const departamentosCollectionIdentifiers = departamentosCollection.map(
        departamentosItem => getDepartamentosIdentifier(departamentosItem)!
      );
      const departamentosToAdd = departamentos.filter(departamentosItem => {
        const departamentosIdentifier = getDepartamentosIdentifier(departamentosItem);
        if (departamentosIdentifier == null || departamentosCollectionIdentifiers.includes(departamentosIdentifier)) {
          return false;
        }
        departamentosCollectionIdentifiers.push(departamentosIdentifier);
        return true;
      });
      return [...departamentosToAdd, ...departamentosCollection];
    }
    return departamentosCollection;
  }
}
