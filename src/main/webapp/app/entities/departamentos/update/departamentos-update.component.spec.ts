import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DepartamentosService } from '../service/departamentos.service';
import { IDepartamentos, Departamentos } from '../departamentos.model';

import { DepartamentosUpdateComponent } from './departamentos-update.component';

describe('Departamentos Management Update Component', () => {
  let comp: DepartamentosUpdateComponent;
  let fixture: ComponentFixture<DepartamentosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let departamentosService: DepartamentosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DepartamentosUpdateComponent],
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
      .overrideTemplate(DepartamentosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DepartamentosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    departamentosService = TestBed.inject(DepartamentosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const departamentos: IDepartamentos = { id: 456 };

      activatedRoute.data = of({ departamentos });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(departamentos));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Departamentos>>();
      const departamentos = { id: 123 };
      jest.spyOn(departamentosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ departamentos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: departamentos }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(departamentosService.update).toHaveBeenCalledWith(departamentos);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Departamentos>>();
      const departamentos = new Departamentos();
      jest.spyOn(departamentosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ departamentos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: departamentos }));
      saveSubject.complete();

      // THEN
      expect(departamentosService.create).toHaveBeenCalledWith(departamentos);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Departamentos>>();
      const departamentos = { id: 123 };
      jest.spyOn(departamentosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ departamentos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(departamentosService.update).toHaveBeenCalledWith(departamentos);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
