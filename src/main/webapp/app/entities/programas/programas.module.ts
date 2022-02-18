import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgramasComponent } from './list/programas.component';
import { ProgramasDetailComponent } from './detail/programas-detail.component';
import { ProgramasUpdateComponent } from './update/programas-update.component';
import { ProgramasDeleteDialogComponent } from './delete/programas-delete-dialog.component';
import { ProgramasRoutingModule } from './route/programas-routing.module';

@NgModule({
  imports: [SharedModule, ProgramasRoutingModule],
  declarations: [ProgramasComponent, ProgramasDetailComponent, ProgramasUpdateComponent, ProgramasDeleteDialogComponent],
  entryComponents: [ProgramasDeleteDialogComponent],
})
export class ProgramasModule {}
