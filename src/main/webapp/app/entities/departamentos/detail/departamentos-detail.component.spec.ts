import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DepartamentosDetailComponent } from './departamentos-detail.component';

describe('Departamentos Management Detail Component', () => {
  let comp: DepartamentosDetailComponent;
  let fixture: ComponentFixture<DepartamentosDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DepartamentosDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ departamentos: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DepartamentosDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DepartamentosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load departamentos on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.departamentos).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
