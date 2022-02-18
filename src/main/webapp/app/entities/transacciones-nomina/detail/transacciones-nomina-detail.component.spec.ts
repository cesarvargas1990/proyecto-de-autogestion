import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TransaccionesNominaDetailComponent } from './transacciones-nomina-detail.component';

describe('TransaccionesNomina Management Detail Component', () => {
  let comp: TransaccionesNominaDetailComponent;
  let fixture: ComponentFixture<TransaccionesNominaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TransaccionesNominaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ transaccionesNomina: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TransaccionesNominaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TransaccionesNominaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load transaccionesNomina on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.transaccionesNomina).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
