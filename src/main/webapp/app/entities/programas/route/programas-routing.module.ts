import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramasComponent } from '../list/programas.component';
import { ProgramasDetailComponent } from '../detail/programas-detail.component';
import { ProgramasUpdateComponent } from '../update/programas-update.component';
import { ProgramasRoutingResolveService } from './programas-routing-resolve.service';

const programasRoute: Routes = [
  {
    path: '',
    component: ProgramasComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramasDetailComponent,
    resolve: {
      programas: ProgramasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProgramasUpdateComponent,
    resolve: {
      programas: ProgramasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProgramasUpdateComponent,
    resolve: {
      programas: ProgramasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(programasRoute)],
  exports: [RouterModule],
})
export class ProgramasRoutingModule {}
