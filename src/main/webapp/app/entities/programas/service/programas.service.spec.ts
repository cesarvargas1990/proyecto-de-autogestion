import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProgramas, Programas } from '../programas.model';

import { ProgramasService } from './programas.service';

describe('Programas Service', () => {
  let service: ProgramasService;
  let httpMock: HttpTestingController;
  let elemDefault: IProgramas;
  let expectedResult: IProgramas | IProgramas[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProgramasService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      idProgramas: 0,
      name: 'AAAAAAA',
      identificacion: 'AAAAAAA',
      fKConvenio: 'AAAAAAA',
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

    it('should create a Programas', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Programas()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Programas', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          idProgramas: 1,
          name: 'BBBBBB',
          identificacion: 'BBBBBB',
          fKConvenio: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Programas', () => {
      const patchObject = Object.assign(
        {
          identificacion: 'BBBBBB',
        },
        new Programas()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Programas', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          idProgramas: 1,
          name: 'BBBBBB',
          identificacion: 'BBBBBB',
          fKConvenio: 'BBBBBB',
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

    it('should delete a Programas', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProgramasToCollectionIfMissing', () => {
      it('should add a Programas to an empty array', () => {
        const programas: IProgramas = { id: 123 };
        expectedResult = service.addProgramasToCollectionIfMissing([], programas);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(programas);
      });

      it('should not add a Programas to an array that contains it', () => {
        const programas: IProgramas = { id: 123 };
        const programasCollection: IProgramas[] = [
          {
            ...programas,
          },
          { id: 456 },
        ];
        expectedResult = service.addProgramasToCollectionIfMissing(programasCollection, programas);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Programas to an array that doesn't contain it", () => {
        const programas: IProgramas = { id: 123 };
        const programasCollection: IProgramas[] = [{ id: 456 }];
        expectedResult = service.addProgramasToCollectionIfMissing(programasCollection, programas);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(programas);
      });

      it('should add only unique Programas to an array', () => {
        const programasArray: IProgramas[] = [{ id: 123 }, { id: 456 }, { id: 59585 }];
        const programasCollection: IProgramas[] = [{ id: 123 }];
        expectedResult = service.addProgramasToCollectionIfMissing(programasCollection, ...programasArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const programas: IProgramas = { id: 123 };
        const programas2: IProgramas = { id: 456 };
        expectedResult = service.addProgramasToCollectionIfMissing([], programas, programas2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(programas);
        expect(expectedResult).toContain(programas2);
      });

      it('should accept null and undefined values', () => {
        const programas: IProgramas = { id: 123 };
        expectedResult = service.addProgramasToCollectionIfMissing([], null, programas, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(programas);
      });

      it('should return initial array if no Programas is added', () => {
        const programasCollection: IProgramas[] = [{ id: 123 }];
        expectedResult = service.addProgramasToCollectionIfMissing(programasCollection, undefined, null);
        expect(expectedResult).toEqual(programasCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
