import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TransaccionesNominaService } from '../service/transacciones-nomina.service';

import { TransaccionesNominaComponent } from './transacciones-nomina.component';

describe('TransaccionesNomina Management Component', () => {
  let comp: TransaccionesNominaComponent;
  let fixture: ComponentFixture<TransaccionesNominaComponent>;
  let service: TransaccionesNominaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TransaccionesNominaComponent],
    })
      .overrideTemplate(TransaccionesNominaComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransaccionesNominaComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TransaccionesNominaService);

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
    expect(comp.transaccionesNominas?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
