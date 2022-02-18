import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramas } from '../programas.model';

@Component({
  selector: 'jhi-programas-detail',
  templateUrl: './programas-detail.component.html',
})
export class ProgramasDetailComponent implements OnInit {
  programas: IProgramas | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programas }) => {
      this.programas = programas;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
