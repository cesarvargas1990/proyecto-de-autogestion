import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDepartamentos, Departamentos } from '../departamentos.model';
import { DepartamentosService } from '../service/departamentos.service';

@Component({
  selector: 'jhi-departamentos-update',
  templateUrl: './departamentos-update.component.html',
})
export class DepartamentosUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idDepartamentos: [],
    name: [],
    codDane: [],
  });

  constructor(protected departamentosService: DepartamentosService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departamentos }) => {
      this.updateForm(departamentos);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const departamentos = this.createFromForm();
    if (departamentos.id !== undefined) {
      this.subscribeToSaveResponse(this.departamentosService.update(departamentos));
    } else {
      this.subscribeToSaveResponse(this.departamentosService.create(departamentos));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartamentos>>): void {
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

  protected updateForm(departamentos: IDepartamentos): void {
    this.editForm.patchValue({
      id: departamentos.id,
      idDepartamentos: departamentos.idDepartamentos,
      name: departamentos.name,
      codDane: departamentos.codDane,
    });
  }

  protected createFromForm(): IDepartamentos {
    return {
      ...new Departamentos(),
      id: this.editForm.get(['id'])!.value,
      idDepartamentos: this.editForm.get(['idDepartamentos'])!.value,
      name: this.editForm.get(['name'])!.value,
      codDane: this.editForm.get(['codDane'])!.value,
    };
  }
}
