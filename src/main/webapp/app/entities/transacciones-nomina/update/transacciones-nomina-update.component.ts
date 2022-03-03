import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITransaccionesNomina, TransaccionesNomina } from '../transacciones-nomina.model';
import { TransaccionesNominaService } from '../service/transacciones-nomina.service';

@Component({
  selector: 'jhi-transacciones-nomina-update',
  templateUrl: './transacciones-nomina-update.component.html',
})
export class TransaccionesNominaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipoDocumentoBenef: [],
    numeroDocumentoBenef: [],
    nombreUno: [],
    nombreDos: [],
    apellidoUno: [],
    apellidoDos: [],
    nombreUnoPago: [],
    nombreDosPago: [],
    apellidoUnoPago: [],
    apellidoDosPago: [],
    fechaPago: [],
    horaPago: [],
    fechaDePago: [],
    estado: [],
    periodoPago: [],
    motivoAnulacion: [],
    fechaVigencia: [],
    fechaCargue: [],
    nota: [],
    redPagadora: [],
    observacionControl: [],
    solicitudAutorizacion: [],
    pinPago: [],
    fKDepartamentoDePago: [],
    fKMunicipioDePago: [],
    fKDepartamento: [],
    fKMunicipio: [],
    fKIdConvenio: [],
    fKIdPrograma: [],
    valorGiro: [],
  });

  constructor(
    protected transaccionesNominaService: TransaccionesNominaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaccionesNomina }) => {
      this.updateForm(transaccionesNomina);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transaccionesNomina = this.createFromForm();
    if (transaccionesNomina.id !== undefined) {
      this.subscribeToSaveResponse(this.transaccionesNominaService.update(transaccionesNomina));
    } else {
      this.subscribeToSaveResponse(this.transaccionesNominaService.create(transaccionesNomina));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaccionesNomina>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(transaccionesNomina: ITransaccionesNomina): void {
    this.editForm.patchValue({
      id: transaccionesNomina.id,
      tipoDocumentoBenef: transaccionesNomina.tipoDocumentoBenef,
      numeroDocumentoBenef: transaccionesNomina.numeroDocumentoBenef,
      nombreUno: transaccionesNomina.nombreUno,
      nombreDos: transaccionesNomina.nombreDos,
      apellidoUno: transaccionesNomina.apellidoUno,
      apellidoDos: transaccionesNomina.apellidoDos,
      nombreUnoPago: transaccionesNomina.nombreUnoPago,
      nombreDosPago: transaccionesNomina.nombreDosPago,
      apellidoUnoPago: transaccionesNomina.apellidoUnoPago,
      apellidoDosPago: transaccionesNomina.apellidoDosPago,
      fechaPago: transaccionesNomina.fechaPago,
      horaPago: transaccionesNomina.horaPago,
      fechaDePago: transaccionesNomina.fechaDePago,
      estado: transaccionesNomina.estado,
      periodoPago: transaccionesNomina.periodoPago,
      motivoAnulacion: transaccionesNomina.motivoAnulacion,
      fechaVigencia: transaccionesNomina.fechaVigencia,
      fechaCargue: transaccionesNomina.fechaCargue,
      nota: transaccionesNomina.nota,
      redPagadora: transaccionesNomina.redPagadora,
      observacionControl: transaccionesNomina.observacionControl,
      solicitudAutorizacion: transaccionesNomina.solicitudAutorizacion,
      pinPago: transaccionesNomina.pinPago,
      fKDepartamentoDePago: transaccionesNomina.fKDepartamentoDePago,
      fKMunicipioDePago: transaccionesNomina.fKMunicipioDePago,
      fKDepartamento: transaccionesNomina.fKDepartamento,
      fKMunicipio: transaccionesNomina.fKMunicipio,
      fKIdConvenio: transaccionesNomina.fKIdConvenio,
      fKIdPrograma: transaccionesNomina.fKIdPrograma,
      valorGiro: transaccionesNomina.valorGiro,
    });
  }

  protected createFromForm(): ITransaccionesNomina {
    return {
      ...new TransaccionesNomina(),
      id: this.editForm.get(['id'])!.value,
      tipoDocumentoBenef: this.editForm.get(['tipoDocumentoBenef'])!.value,
      numeroDocumentoBenef: this.editForm.get(['numeroDocumentoBenef'])!.value,
      nombreUno: this.editForm.get(['nombreUno'])!.value,
      nombreDos: this.editForm.get(['nombreDos'])!.value,
      apellidoUno: this.editForm.get(['apellidoUno'])!.value,
      apellidoDos: this.editForm.get(['apellidoDos'])!.value,
      nombreUnoPago: this.editForm.get(['nombreUnoPago'])!.value,
      nombreDosPago: this.editForm.get(['nombreDosPago'])!.value,
      apellidoUnoPago: this.editForm.get(['apellidoUnoPago'])!.value,
      apellidoDosPago: this.editForm.get(['apellidoDosPago'])!.value,
      fechaPago: this.editForm.get(['fechaPago'])!.value,
      horaPago: this.editForm.get(['horaPago'])!.value,
      fechaDePago: this.editForm.get(['fechaDePago'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      periodoPago: this.editForm.get(['periodoPago'])!.value,
      motivoAnulacion: this.editForm.get(['motivoAnulacion'])!.value,
      fechaVigencia: this.editForm.get(['fechaVigencia'])!.value,
      fechaCargue: this.editForm.get(['fechaCargue'])!.value,
      nota: this.editForm.get(['nota'])!.value,
      redPagadora: this.editForm.get(['redPagadora'])!.value,
      observacionControl: this.editForm.get(['observacionControl'])!.value,
      solicitudAutorizacion: this.editForm.get(['solicitudAutorizacion'])!.value,
      pinPago: this.editForm.get(['pinPago'])!.value,
      fKDepartamentoDePago: this.editForm.get(['fKDepartamentoDePago'])!.value,
      fKMunicipioDePago: this.editForm.get(['fKMunicipioDePago'])!.value,
      fKDepartamento: this.editForm.get(['fKDepartamento'])!.value,
      fKMunicipio: this.editForm.get(['fKMunicipio'])!.value,
      fKIdConvenio: this.editForm.get(['fKIdConvenio'])!.value,
      fKIdPrograma: this.editForm.get(['fKIdPrograma'])!.value,
      valorGiro: this.editForm.get(['valorGiro'])!.value,
    };
  }
}
