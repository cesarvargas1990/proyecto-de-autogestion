import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ProgramasService } from '../service/programas.service';

import { ProgramasComponent } from './programas.component';

describe('Programas Management Component', () => {
  let comp: ProgramasComponent;
  let fixture: ComponentFixture<ProgramasComponent>;
  let service: ProgramasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ProgramasComponent],
    })
      .overrideTemplate(ProgramasComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProgramasComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProgramasService);

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
    expect(comp.programas?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
