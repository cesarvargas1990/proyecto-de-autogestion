export interface IDepartamentos {
  id?: number;
  name?: string | null;
  codDane?: number | null;
}

export class Departamentos implements IDepartamentos {
  constructor(public id?: number, public name?: string | null, public codDane?: number | null) {}
}

export function getDepartamentosIdentifier(departamentos: IDepartamentos): number | undefined {
  return departamentos.id;
}
