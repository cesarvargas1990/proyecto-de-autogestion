import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProgramasService } from '../service/programas.service';
import { IProgramas, Programas } from '../programas.model';

import { ProgramasUpdateComponent } from './programas-update.component';

describe('Programas Management Update Component', () => {
  let comp: ProgramasUpdateComponent;
  let fixture: ComponentFixture<ProgramasUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let programasService: ProgramasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProgramasUpdateComponent],
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
      .overrideTemplate(ProgramasUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProgramasUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    programasService = TestBed.inject(ProgramasService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const programas: IProgramas = { id: 456 };

      activatedRoute.data = of({ programas });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(programas));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Programas>>();
      const programas = { id: 123 };
      jest.spyOn(programasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programas }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(programasService.update).toHaveBeenCalledWith(programas);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Programas>>();
      const programas = new Programas();
      jest.spyOn(programasService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programas }));
      saveSubject.complete();

      // THEN
      expect(programasService.create).toHaveBeenCalledWith(programas);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Programas>>();
      const programas = { id: 123 };
      jest.spyOn(programasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(programasService.update).toHaveBeenCalledWith(programas);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
