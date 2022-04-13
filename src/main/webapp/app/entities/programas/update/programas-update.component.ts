import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IProgramas, Programas } from '../programas.model';
import { ProgramasService } from '../service/programas.service';

@Component({
  selector: 'jhi-programas-update',
  templateUrl: './programas-update.component.html',
})
export class ProgramasUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idProgramas: [],
    name: [],
    identificacion: [],
    fKConvenio: [],
  });

  constructor(protected programasService: ProgramasService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programas }) => {
      this.updateForm(programas);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const programas = this.createFromForm();
    if (programas.id !== undefined) {
      this.subscribeToSaveResponse(this.programasService.update(programas));
    } else {
      this.subscribeToSaveResponse(this.programasService.create(programas));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramas>>): void {
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

  protected updateForm(programas: IProgramas): void {
    this.editForm.patchValue({
      id: programas.id,
      idProgramas: programas.idProgramas,
      name: programas.name,
      identificacion: programas.identificacion,
      fKConvenio: programas.fKConvenio,
    });
  }

  protected createFromForm(): IProgramas {
    return {
      ...new Programas(),
      id: this.editForm.get(['id'])!.value,
      idProgramas: this.editForm.get(['idProgramas'])!.value,
      name: this.editForm.get(['name'])!.value,
      identificacion: this.editForm.get(['identificacion'])!.value,
      fKConvenio: this.editForm.get(['fKConvenio'])!.value,
    };
  }
}
