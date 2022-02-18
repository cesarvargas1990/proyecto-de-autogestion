import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDepartamentos } from '../departamentos.model';
import { DepartamentosService } from '../service/departamentos.service';
import { DepartamentosDeleteDialogComponent } from '../delete/departamentos-delete-dialog.component';

@Component({
  selector: 'jhi-departamentos',
  templateUrl: './departamentos.component.html',
})
export class DepartamentosComponent implements OnInit {
  departamentos?: IDepartamentos[];
  isLoading = false;

  constructor(protected departamentosService: DepartamentosService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.departamentosService.query().subscribe({
      next: (res: HttpResponse<IDepartamentos[]>) => {
        this.isLoading = false;
        this.departamentos = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDepartamentos): number {
    return item.id!;
  }

  delete(departamentos: IDepartamentos): void {
    const modalRef = this.modalService.open(DepartamentosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.departamentos = departamentos;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
