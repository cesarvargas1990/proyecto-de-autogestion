import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProgramasDetailComponent } from './programas-detail.component';

describe('Programas Management Detail Component', () => {
  let comp: ProgramasDetailComponent;
  let fixture: ComponentFixture<ProgramasDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgramasDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ programas: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProgramasDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProgramasDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load programas on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.programas).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
