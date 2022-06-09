import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransaccionesNomina, getTransaccionesNominaIdentifier } from '../transacciones-nomina.model';
import { TIRILLA_URI } from 'app/app.constants';

export type EntityResponseType = HttpResponse<ITransaccionesNomina>;
export type EntityArrayResponseType = HttpResponse<ITransaccionesNomina[]>;

@Injectable({ providedIn: 'root' })
export class TransaccionesNominaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transacciones-nominas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transaccionesNomina: ITransaccionesNomina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transaccionesNomina);
    return this.http
      .post<ITransaccionesNomina>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transaccionesNomina: ITransaccionesNomina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transaccionesNomina);
    return this.http
      .put<ITransaccionesNomina>(`${this.resourceUrl}/${getTransaccionesNominaIdentifier(transaccionesNomina) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(transaccionesNomina: ITransaccionesNomina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transaccionesNomina);
    return this.http
      .patch<ITransaccionesNomina>(`${this.resourceUrl}/${getTransaccionesNominaIdentifier(transaccionesNomina) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransaccionesNomina>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  // findDepartmentsCodDaneById(ids: string[]): Observable<{}> {
  //   return this.http
  //     .get<string[]>(this.applicationConfigService.getEndpointFor('api/transacciones-nominas') + "/department", { params: ids});
  // }

  findByDocument(typeDocument: string, numberDocument: number, idNomina: string, idUser: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<ITransaccionesNomina[]>(
        'api/transacciones-nominas/search?typeDocument=' +
          `${typeDocument}` +
          '&numberDocument=' +
          `${numberDocument}` +
          '&idNomina=' +
          `${idNomina}` +
          '&idUser=' +
          `${idUser}`,
        {
          observe: 'response',
        }
      )
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  findByDocumentAllDepartments(numberDocument: number, typeDocument: string): Observable<EntityArrayResponseType> {
    return this.http
      .get<ITransaccionesNomina[]>(`${this.resourceUrl}/${typeDocument}/${numberDocument}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  findTirillas(pin: string, documentNumber: string): Observable<any> {
    const httpOptions = {
      responseType: 'blob' as 'json',
    };
    return this.http.get(
      this.applicationConfigService.getEndpointFor('api/getTirilla?pin=' + `${pin}` + '&numberDocument=' + `${documentNumber}`),
      httpOptions
    );
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransaccionesNomina[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTransaccionesNominaToCollectionIfMissing(
    transaccionesNominaCollection: ITransaccionesNomina[],
    ...transaccionesNominasToCheck: (ITransaccionesNomina | null | undefined)[]
  ): ITransaccionesNomina[] {
    const transaccionesNominas: ITransaccionesNomina[] = transaccionesNominasToCheck.filter(isPresent);
    if (transaccionesNominas.length > 0) {
      const transaccionesNominaCollectionIdentifiers = transaccionesNominaCollection.map(
        transaccionesNominaItem => getTransaccionesNominaIdentifier(transaccionesNominaItem)!
      );
      const transaccionesNominasToAdd = transaccionesNominas.filter(transaccionesNominaItem => {
        const transaccionesNominaIdentifier = getTransaccionesNominaIdentifier(transaccionesNominaItem);
        if (transaccionesNominaIdentifier == null || transaccionesNominaCollectionIdentifiers.includes(transaccionesNominaIdentifier)) {
          return false;
        }
        transaccionesNominaCollectionIdentifiers.push(transaccionesNominaIdentifier);
        return true;
      });
      return [...transaccionesNominasToAdd, ...transaccionesNominaCollection];
    }
    return transaccionesNominaCollection;
  }

  protected convertDateFromClient(transaccionesNomina: ITransaccionesNomina): ITransaccionesNomina {
    return Object.assign({}, transaccionesNomina, {
      fechaPago: transaccionesNomina.fechaPago?.isValid() ? transaccionesNomina.fechaPago.format(DATE_FORMAT) : undefined,
      fechaDePago: transaccionesNomina.fechaDePago?.isValid() ? transaccionesNomina.fechaDePago.format(DATE_FORMAT) : undefined,
      fechaVigencia: transaccionesNomina.fechaVigencia?.isValid() ? transaccionesNomina.fechaVigencia.format(DATE_FORMAT) : undefined,
      fechaCargue: transaccionesNomina.fechaCargue?.isValid() ? transaccionesNomina.fechaCargue.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaPago = res.body.fechaPago ? dayjs(res.body.fechaPago) : undefined;
      res.body.fechaDePago = res.body.fechaDePago ? dayjs(res.body.fechaDePago) : undefined;
      res.body.fechaVigencia = res.body.fechaVigencia ? dayjs(res.body.fechaVigencia) : undefined;
      res.body.fechaCargue = res.body.fechaCargue ? dayjs(res.body.fechaCargue) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transaccionesNomina: ITransaccionesNomina) => {
        transaccionesNomina.fechaPago = transaccionesNomina.fechaPago ? dayjs(transaccionesNomina.fechaPago) : undefined;
        transaccionesNomina.fechaDePago = transaccionesNomina.fechaDePago ? dayjs(transaccionesNomina.fechaDePago) : undefined;
        transaccionesNomina.fechaVigencia = transaccionesNomina.fechaVigencia ? dayjs(transaccionesNomina.fechaVigencia) : undefined;
        transaccionesNomina.fechaCargue = transaccionesNomina.fechaCargue ? dayjs(transaccionesNomina.fechaCargue) : undefined;
      });
    }
    return res;
  }
}
