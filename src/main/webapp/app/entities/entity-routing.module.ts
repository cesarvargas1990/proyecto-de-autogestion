import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'transacciones-nomina',
        data: { pageTitle: 'portalautogestionApp.transaccionesNomina.home.title' },
        loadChildren: () => import('./transacciones-nomina/transacciones-nomina.module').then(m => m.TransaccionesNominaModule),
      },
      {
        path: 'departamentos',
        data: { pageTitle: 'portalautogestionApp.departamentos.home.title' },
        loadChildren: () => import('./departamentos/departamentos.module').then(m => m.DepartamentosModule),
      },
      {
        path: 'municipio',
        data: { pageTitle: 'portalautogestionApp.municipio.home.title' },
        loadChildren: () => import('./municipio/municipio.module').then(m => m.MunicipioModule),
      },
      {
        path: 'convenio',
        data: { pageTitle: 'portalautogestionApp.convenio.home.title' },
        loadChildren: () => import('./convenio/convenio.module').then(m => m.ConvenioModule),
      },
      {
        path: 'programas',
        data: { pageTitle: 'portalautogestionApp.programas.home.title' },
        loadChildren: () => import('./programas/programas.module').then(m => m.ProgramasModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
