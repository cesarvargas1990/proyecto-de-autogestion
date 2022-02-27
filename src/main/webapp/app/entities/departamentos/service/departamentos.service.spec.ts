import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDepartamentos, Departamentos } from '../departamentos.model';

import { DepartamentosService } from './departamentos.service';

describe('Departamentos Service', () => {
  let service: DepartamentosService;
  let httpMock: HttpTestingController;
  let elemDefault: IDepartamentos;
  let expectedResult: IDepartamentos | IDepartamentos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DepartamentosService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      codDane: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Departamentos', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Departamentos()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Departamentos', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          codDane: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Departamentos', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          codDane: 1,
        },
        new Departamentos()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Departamentos', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          codDane: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Departamentos', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDepartamentosToCollectionIfMissing', () => {
      it('should add a Departamentos to an empty array', () => {
        const departamentos: IDepartamentos = { id: 123 };
        expectedResult = service.addDepartamentosToCollectionIfMissing([], departamentos);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(departamentos);
      });

      it('should not add a Departamentos to an array that contains it', () => {
        const departamentos: IDepartamentos = { id: 123 };
        const departamentosCollection: IDepartamentos[] = [
          {
            ...departamentos,
          },
          { id: 456 },
        ];
        expectedResult = service.addDepartamentosToCollectionIfMissing(departamentosCollection, departamentos);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Departamentos to an array that doesn't contain it", () => {
        const departamentos: IDepartamentos = { id: 123 };
        const departamentosCollection: IDepartamentos[] = [{ id: 456 }];
        expectedResult = service.addDepartamentosToCollectionIfMissing(departamentosCollection, departamentos);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(departamentos);
      });

      it('should add only unique Departamentos to an array', () => {
        const departamentosArray: IDepartamentos[] = [{ id: 123 }, { id: 456 }, { id: 41528 }];
        const departamentosCollection: IDepartamentos[] = [{ id: 123 }];
        expectedResult = service.addDepartamentosToCollectionIfMissing(departamentosCollection, ...departamentosArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const departamentos: IDepartamentos = { id: 123 };
        const departamentos2: IDepartamentos = { id: 456 };
        expectedResult = service.addDepartamentosToCollectionIfMissing([], departamentos, departamentos2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(departamentos);
        expect(expectedResult).toContain(departamentos2);
      });

      it('should accept null and undefined values', () => {
        const departamentos: IDepartamentos = { id: 123 };
        expectedResult = service.addDepartamentosToCollectionIfMissing([], null, departamentos, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(departamentos);
      });

      it('should return initial array if no Departamentos is added', () => {
        const departamentosCollection: IDepartamentos[] = [{ id: 123 }];
        expectedResult = service.addDepartamentosToCollectionIfMissing(departamentosCollection, undefined, null);
        expect(expectedResult).toEqual(departamentosCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
