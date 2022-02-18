import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDepartamentos, Departamentos } from '../departamentos.model';
import { DepartamentosService } from '../service/departamentos.service';

@Injectable({ providedIn: 'root' })
export class DepartamentosRoutingResolveService implements Resolve<IDepartamentos> {
  constructor(protected service: DepartamentosService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDepartamentos> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((departamentos: HttpResponse<Departamentos>) => {
          if (departamentos.body) {
            return of(departamentos.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Departamentos());
  }
}
