import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransaccionesNomina } from '../transacciones-nomina.model';

@Component({
  selector: 'jhi-transacciones-nomina-detail',
  templateUrl: './transacciones-nomina-detail.component.html',
})
export class TransaccionesNominaDetailComponent implements OnInit {
  transaccionesNomina: ITransaccionesNomina | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaccionesNomina }) => {
      this.transaccionesNomina = transaccionesNomina;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
