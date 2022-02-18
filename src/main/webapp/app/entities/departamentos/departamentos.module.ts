import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DepartamentosComponent } from './list/departamentos.component';
import { DepartamentosDetailComponent } from './detail/departamentos-detail.component';
import { DepartamentosUpdateComponent } from './update/departamentos-update.component';
import { DepartamentosDeleteDialogComponent } from './delete/departamentos-delete-dialog.component';
import { DepartamentosRoutingModule } from './route/departamentos-routing.module';

@NgModule({
  imports: [SharedModule, DepartamentosRoutingModule],
  declarations: [DepartamentosComponent, DepartamentosDetailComponent, DepartamentosUpdateComponent, DepartamentosDeleteDialogComponent],
  entryComponents: [DepartamentosDeleteDialogComponent],
})
export class DepartamentosModule {}
