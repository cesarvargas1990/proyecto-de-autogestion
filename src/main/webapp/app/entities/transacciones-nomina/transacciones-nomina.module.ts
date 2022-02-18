import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TransaccionesNominaComponent } from './list/transacciones-nomina.component';
import { TransaccionesNominaDetailComponent } from './detail/transacciones-nomina-detail.component';
import { TransaccionesNominaUpdateComponent } from './update/transacciones-nomina-update.component';
import { TransaccionesNominaDeleteDialogComponent } from './delete/transacciones-nomina-delete-dialog.component';
import { TransaccionesNominaRoutingModule } from './route/transacciones-nomina-routing.module';

@NgModule({
  imports: [SharedModule, TransaccionesNominaRoutingModule],
  declarations: [
    TransaccionesNominaComponent,
    TransaccionesNominaDetailComponent,
    TransaccionesNominaUpdateComponent,
    TransaccionesNominaDeleteDialogComponent,
  ],
  entryComponents: [TransaccionesNominaDeleteDialogComponent],
})
export class TransaccionesNominaModule {}
