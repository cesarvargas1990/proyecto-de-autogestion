import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TransaccionesNominaComponent } from '../list/transacciones-nomina.component';
import { TransaccionesNominaDetailComponent } from '../detail/transacciones-nomina-detail.component';
import { TransaccionesNominaUpdateComponent } from '../update/transacciones-nomina-update.component';
import { TransaccionesNominaRoutingResolveService } from './transacciones-nomina-routing-resolve.service';

const transaccionesNominaRoute: Routes = [
  {
    path: '',
    component: TransaccionesNominaComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransaccionesNominaDetailComponent,
    resolve: {
      transaccionesNomina: TransaccionesNominaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransaccionesNominaUpdateComponent,
    resolve: {
      transaccionesNomina: TransaccionesNominaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransaccionesNominaUpdateComponent,
    resolve: {
      transaccionesNomina: TransaccionesNominaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transaccionesNominaRoute)],
  exports: [RouterModule],
})
export class TransaccionesNominaRoutingModule {}
