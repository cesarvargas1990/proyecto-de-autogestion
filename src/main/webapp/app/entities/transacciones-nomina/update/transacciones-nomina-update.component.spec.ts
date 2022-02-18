import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransaccionesNominaService } from '../service/transacciones-nomina.service';
import { ITransaccionesNomina, TransaccionesNomina } from '../transacciones-nomina.model';

import { TransaccionesNominaUpdateComponent } from './transacciones-nomina-update.component';

describe('TransaccionesNomina Management Update Component', () => {
  let comp: TransaccionesNominaUpdateComponent;
  let fixture: ComponentFixture<TransaccionesNominaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transaccionesNominaService: TransaccionesNominaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransaccionesNominaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TransaccionesNominaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransaccionesNominaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transaccionesNominaService = TestBed.inject(TransaccionesNominaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const transaccionesNomina: ITransaccionesNomina = { id: 456 };

      activatedRoute.data = of({ transaccionesNomina });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(transaccionesNomina));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransaccionesNomina>>();
      const transaccionesNomina = { id: 123 };
      jest.spyOn(transaccionesNominaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transaccionesNomina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transaccionesNomina }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(transaccionesNominaService.update).toHaveBeenCalledWith(transaccionesNomina);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransaccionesNomina>>();
      const transaccionesNomina = new TransaccionesNomina();
      jest.spyOn(transaccionesNominaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transaccionesNomina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transaccionesNomina }));
      saveSubject.complete();

      // THEN
      expect(transaccionesNominaService.create).toHaveBeenCalledWith(transaccionesNomina);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TransaccionesNomina>>();
      const transaccionesNomina = { id: 123 };
      jest.spyOn(transaccionesNominaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transaccionesNomina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transaccionesNominaService.update).toHaveBeenCalledWith(transaccionesNomina);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
