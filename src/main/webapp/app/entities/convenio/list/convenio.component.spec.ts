import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ConvenioService } from '../service/convenio.service';

import { ConvenioComponent } from './convenio.component';

describe('Convenio Management Component', () => {
  let comp: ConvenioComponent;
  let fixture: ComponentFixture<ConvenioComponent>;
  let service: ConvenioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ConvenioComponent],
    })
      .overrideTemplate(ConvenioComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ConvenioComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ConvenioService);

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
    expect(comp.convenios?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
