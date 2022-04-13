export interface IProgramas {
  id?: number;
  idProgramas?: number | null;
  name?: string | null;
  identificacion?: string | null;
  fKConvenio?: string | null;
}

export class Programas implements IProgramas {
  constructor(
    public id?: number,
    public idProgramas?: number | null,
    public name?: string | null,
    public identificacion?: string | null,
    public fKConvenio?: string | null
  ) {}
}

export function getProgramasIdentifier(programas: IProgramas): number | undefined {
  return programas.id;
}
