import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITransaccionesNomina, TransaccionesNomina } from '../transacciones-nomina.model';
import { TransaccionesNominaService } from '../service/transacciones-nomina.service';

@Injectable({ providedIn: 'root' })
export class TransaccionesNominaRoutingResolveService implements Resolve<ITransaccionesNomina> {
  constructor(protected service: TransaccionesNominaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransaccionesNomina> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((transaccionesNomina: HttpResponse<TransaccionesNomina>) => {
          if (transaccionesNomina.body) {
            return of(transaccionesNomina.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransaccionesNomina());
  }
}
