import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProgramas } from '../programas.model';
import { ProgramasService } from '../service/programas.service';
import { ProgramasDeleteDialogComponent } from '../delete/programas-delete-dialog.component';

@Component({
  selector: 'jhi-programas',
  templateUrl: './programas.component.html',
})
export class ProgramasComponent implements OnInit {
  programas?: IProgramas[];
  isLoading = false;

  constructor(protected programasService: ProgramasService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.programasService.query().subscribe({
      next: (res: HttpResponse<IProgramas[]>) => {
        this.isLoading = false;
        this.programas = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IProgramas): number {
    return item.id!;
  }

  delete(programas: IProgramas): void {
    const modalRef = this.modalService.open(ProgramasDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.programas = programas;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
