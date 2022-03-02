import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProgramas, getProgramasIdentifier } from '../programas.model';

export type EntityResponseType = HttpResponse<IProgramas>;
export type EntityArrayResponseType = HttpResponse<IProgramas[]>;

@Injectable({ providedIn: 'root' })
export class ProgramasService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/programas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(programas: IProgramas): Observable<EntityResponseType> {
    return this.http.post<IProgramas>(this.resourceUrl, programas, { observe: 'response' });
  }

  update(programas: IProgramas): Observable<EntityResponseType> {
    return this.http.put<IProgramas>(`${this.resourceUrl}/${getProgramasIdentifier(programas) as number}`, programas, {
      observe: 'response',
    });
  }

  partialUpdate(programas: IProgramas): Observable<EntityResponseType> {
    return this.http.patch<IProgramas>(`${this.resourceUrl}/${getProgramasIdentifier(programas) as number}`, programas, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProgramas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByNit(nit: number): Observable<EntityResponseType> {
    return this.http.get<IProgramas>(`${this.resourceUrl}/nit/${nit}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProgramas[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProgramasToCollectionIfMissing(
    programasCollection: IProgramas[],
    ...programasToCheck: (IProgramas | null | undefined)[]
  ): IProgramas[] {
    const programas: IProgramas[] = programasToCheck.filter(isPresent);
    if (programas.length > 0) {
      const programasCollectionIdentifiers = programasCollection.map(programasItem => getProgramasIdentifier(programasItem)!);
      const programasToAdd = programas.filter(programasItem => {
        const programasIdentifier = getProgramasIdentifier(programasItem);
        if (programasIdentifier == null || programasCollectionIdentifiers.includes(programasIdentifier)) {
          return false;
        }
        programasCollectionIdentifiers.push(programasIdentifier);
        return true;
      });
      return [...programasToAdd, ...programasCollection];
    }
    return programasCollection;
  }
}
