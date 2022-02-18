import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramas, Programas } from '../programas.model';
import { ProgramasService } from '../service/programas.service';

@Injectable({ providedIn: 'root' })
export class ProgramasRoutingResolveService implements Resolve<IProgramas> {
  constructor(protected service: ProgramasService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProgramas> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((programas: HttpResponse<Programas>) => {
          if (programas.body) {
            return of(programas.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Programas());
  }
}
