import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultaEstadoGiroComponent } from './consulta-estado-giro.component';

describe('ConsultaEstadoGiroComponent', () => {
  let component: ConsultaEstadoGiroComponent;
  let fixture: ComponentFixture<ConsultaEstadoGiroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsultaEstadoGiroComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultaEstadoGiroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
