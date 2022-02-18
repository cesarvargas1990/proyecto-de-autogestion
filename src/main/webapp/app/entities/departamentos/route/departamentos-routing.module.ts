import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DepartamentosComponent } from '../list/departamentos.component';
import { DepartamentosDetailComponent } from '../detail/departamentos-detail.component';
import { DepartamentosUpdateComponent } from '../update/departamentos-update.component';
import { DepartamentosRoutingResolveService } from './departamentos-routing-resolve.service';

const departamentosRoute: Routes = [
  {
    path: '',
    component: DepartamentosComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DepartamentosDetailComponent,
    resolve: {
      departamentos: DepartamentosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DepartamentosUpdateComponent,
    resolve: {
      departamentos: DepartamentosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DepartamentosUpdateComponent,
    resolve: {
      departamentos: DepartamentosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(departamentosRoute)],
  exports: [RouterModule],
})
export class DepartamentosRoutingModule {}
