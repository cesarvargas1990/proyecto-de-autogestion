import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DepartamentosService } from '../service/departamentos.service';

import { DepartamentosComponent } from './departamentos.component';

describe('Departamentos Management Component', () => {
  let comp: DepartamentosComponent;
  let fixture: ComponentFixture<DepartamentosComponent>;
  let service: DepartamentosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DepartamentosComponent],
    })
      .overrideTemplate(DepartamentosComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DepartamentosComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DepartamentosService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.departamentos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
