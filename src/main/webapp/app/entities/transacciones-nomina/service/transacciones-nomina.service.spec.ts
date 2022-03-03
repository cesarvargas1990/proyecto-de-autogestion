import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITransaccionesNomina, TransaccionesNomina } from '../transacciones-nomina.model';

import { TransaccionesNominaService } from './transacciones-nomina.service';

describe('TransaccionesNomina Service', () => {
  let service: TransaccionesNominaService;
  let httpMock: HttpTestingController;
  let elemDefault: ITransaccionesNomina;
  let expectedResult: ITransaccionesNomina | ITransaccionesNomina[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TransaccionesNominaService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      tipoDocumentoBenef: 'AAAAAAA',
      numeroDocumentoBenef: 0,
      nombreUno: 'AAAAAAA',
      nombreDos: 'AAAAAAA',
      apellidoUno: 'AAAAAAA',
      apellidoDos: 'AAAAAAA',
      nombreUnoPago: 'AAAAAAA',
      nombreDosPago: 'AAAAAAA',
      apellidoUnoPago: 'AAAAAAA',
      apellidoDosPago: 'AAAAAAA',
      fechaPago: currentDate,
      horaPago: 'AAAAAAA',
      fechaDePago: currentDate,
      estado: 'AAAAAAA',
      periodoPago: 'AAAAAAA',
      motivoAnulacion: 'AAAAAAA',
      fechaVigencia: currentDate,
      fechaCargue: currentDate,
      nota: 'AAAAAAA',
      redPagadora: 'AAAAAAA',
      observacionControl: 'AAAAAAA',
      solicitudAutorizacion: 'AAAAAAA',
      pinPago: 'AAAAAAA',
      fKDepartamentoDePago: 'AAAAAAA',
      fKMunicipioDePago: 'AAAAAAA',
      fKDepartamento: 'AAAAAAA',
      fKMunicipio: 'AAAAAAA',
      fKIdConvenio: 'AAAAAAA',
      fKIdPrograma: 'AAAAAAA',
      valorGiro: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          fechaPago: currentDate.format(DATE_FORMAT),
          fechaDePago: currentDate.format(DATE_FORMAT),
          fechaVigencia: currentDate.format(DATE_FORMAT),
          fechaCargue: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TransaccionesNomina', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          fechaPago: currentDate.format(DATE_FORMAT),
          fechaDePago: currentDate.format(DATE_FORMAT),
          fechaVigencia: currentDate.format(DATE_FORMAT),
          fechaCargue: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaPago: currentDate,
          fechaDePago: currentDate,
          fechaVigencia: currentDate,
          fechaCargue: currentDate,
        },
        returnedFromService
      );

      service.create(new TransaccionesNomina()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TransaccionesNomina', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tipoDocumentoBenef: 'BBBBBB',
          numeroDocumentoBenef: 1,
          nombreUno: 'BBBBBB',
          nombreDos: 'BBBBBB',
          apellidoUno: 'BBBBBB',
          apellidoDos: 'BBBBBB',
          nombreUnoPago: 'BBBBBB',
          nombreDosPago: 'BBBBBB',
          apellidoUnoPago: 'BBBBBB',
          apellidoDosPago: 'BBBBBB',
          fechaPago: currentDate.format(DATE_FORMAT),
          horaPago: 'BBBBBB',
          fechaDePago: currentDate.format(DATE_FORMAT),
          estado: 'BBBBBB',
          periodoPago: 'BBBBBB',
          motivoAnulacion: 'BBBBBB',
          fechaVigencia: currentDate.format(DATE_FORMAT),
          fechaCargue: currentDate.format(DATE_FORMAT),
          nota: 'BBBBBB',
          redPagadora: 'BBBBBB',
          observacionControl: 'BBBBBB',
          solicitudAutorizacion: 'BBBBBB',
          pinPago: 'BBBBBB',
          fKDepartamentoDePago: 'BBBBBB',
          fKMunicipioDePago: 'BBBBBB',
          fKDepartamento: 'BBBBBB',
          fKMunicipio: 'BBBBBB',
          fKIdConvenio: 'BBBBBB',
          fKIdPrograma: 'BBBBBB',
          valorGiro: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaPago: currentDate,
          fechaDePago: currentDate,
          fechaVigencia: currentDate,
          fechaCargue: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TransaccionesNomina', () => {
      const patchObject = Object.assign(
        {
          nombreUno: 'BBBBBB',
          apellidoUno: 'BBBBBB',
          nombreUnoPago: 'BBBBBB',
          apellidoUnoPago: 'BBBBBB',
          fechaPago: currentDate.format(DATE_FORMAT),
          horaPago: 'BBBBBB',
          periodoPago: 'BBBBBB',
          nota: 'BBBBBB',
          solicitudAutorizacion: 'BBBBBB',
          pinPago: 'BBBBBB',
          fKDepartamentoDePago: 'BBBBBB',
          fKIdConvenio: 'BBBBBB',
        },
        new TransaccionesNomina()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          fechaPago: currentDate,
          fechaDePago: currentDate,
          fechaVigencia: currentDate,
          fechaCargue: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TransaccionesNomina', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tipoDocumentoBenef: 'BBBBBB',
          numeroDocumentoBenef: 1,
          nombreUno: 'BBBBBB',
          nombreDos: 'BBBBBB',
          apellidoUno: 'BBBBBB',
          apellidoDos: 'BBBBBB',
          nombreUnoPago: 'BBBBBB',
          nombreDosPago: 'BBBBBB',
          apellidoUnoPago: 'BBBBBB',
          apellidoDosPago: 'BBBBBB',
          fechaPago: currentDate.format(DATE_FORMAT),
          horaPago: 'BBBBBB',
          fechaDePago: currentDate.format(DATE_FORMAT),
          estado: 'BBBBBB',
          periodoPago: 'BBBBBB',
          motivoAnulacion: 'BBBBBB',
          fechaVigencia: currentDate.format(DATE_FORMAT),
          fechaCargue: currentDate.format(DATE_FORMAT),
          nota: 'BBBBBB',
          redPagadora: 'BBBBBB',
          observacionControl: 'BBBBBB',
          solicitudAutorizacion: 'BBBBBB',
          pinPago: 'BBBBBB',
          fKDepartamentoDePago: 'BBBBBB',
          fKMunicipioDePago: 'BBBBBB',
          fKDepartamento: 'BBBBBB',
          fKMunicipio: 'BBBBBB',
          fKIdConvenio: 'BBBBBB',
          fKIdPrograma: 'BBBBBB',
          valorGiro: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          fechaPago: currentDate,
          fechaDePago: currentDate,
          fechaVigencia: currentDate,
          fechaCargue: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TransaccionesNomina', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTransaccionesNominaToCollectionIfMissing', () => {
      it('should add a TransaccionesNomina to an empty array', () => {
        const transaccionesNomina: ITransaccionesNomina = { id: 123 };
        expectedResult = service.addTransaccionesNominaToCollectionIfMissing([], transaccionesNomina);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transaccionesNomina);
      });

      it('should not add a TransaccionesNomina to an array that contains it', () => {
        const transaccionesNomina: ITransaccionesNomina = { id: 123 };
        const transaccionesNominaCollection: ITransaccionesNomina[] = [
          {
            ...transaccionesNomina,
          },
          { id: 456 },
        ];
        expectedResult = service.addTransaccionesNominaToCollectionIfMissing(transaccionesNominaCollection, transaccionesNomina);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TransaccionesNomina to an array that doesn't contain it", () => {
        const transaccionesNomina: ITransaccionesNomina = { id: 123 };
        const transaccionesNominaCollection: ITransaccionesNomina[] = [{ id: 456 }];
        expectedResult = service.addTransaccionesNominaToCollectionIfMissing(transaccionesNominaCollection, transaccionesNomina);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transaccionesNomina);
      });

      it('should add only unique TransaccionesNomina to an array', () => {
        const transaccionesNominaArray: ITransaccionesNomina[] = [{ id: 123 }, { id: 456 }, { id: 44924 }];
        const transaccionesNominaCollection: ITransaccionesNomina[] = [{ id: 123 }];
        expectedResult = service.addTransaccionesNominaToCollectionIfMissing(transaccionesNominaCollection, ...transaccionesNominaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transaccionesNomina: ITransaccionesNomina = { id: 123 };
        const transaccionesNomina2: ITransaccionesNomina = { id: 456 };
        expectedResult = service.addTransaccionesNominaToCollectionIfMissing([], transaccionesNomina, transaccionesNomina2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transaccionesNomina);
        expect(expectedResult).toContain(transaccionesNomina2);
      });

      it('should accept null and undefined values', () => {
        const transaccionesNomina: ITransaccionesNomina = { id: 123 };
        expectedResult = service.addTransaccionesNominaToCollectionIfMissing([], null, transaccionesNomina, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transaccionesNomina);
      });

      it('should return initial array if no TransaccionesNomina is added', () => {
        const transaccionesNominaCollection: ITransaccionesNomina[] = [{ id: 123 }];
        expectedResult = service.addTransaccionesNominaToCollectionIfMissing(transaccionesNominaCollection, undefined, null);
        expect(expectedResult).toEqual(transaccionesNominaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
