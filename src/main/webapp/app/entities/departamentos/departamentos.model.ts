export interface IDepartamentos {
  id?: number;
  idDepartamentos?: number | null;
  name?: string | null;
  codDane?: number | null;
}

export class Departamentos implements IDepartamentos {
  constructor(public id?: number, public idDepartamentos?: number | null, public name?: string | null, public codDane?: number | null) {}
}

export function getDepartamentosIdentifier(departamentos: IDepartamentos): number | undefined {
  return departamentos.id;
}
