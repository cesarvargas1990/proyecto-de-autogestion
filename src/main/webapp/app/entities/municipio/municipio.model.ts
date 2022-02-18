export interface IMunicipio {
  id?: number;
  idMunicipio?: number | null;
  name?: string | null;
  codDane?: number | null;
  fKIdDepartamento?: number | null;
}

export class Municipio implements IMunicipio {
  constructor(
    public id?: number,
    public idMunicipio?: number | null,
    public name?: string | null,
    public codDane?: number | null,
    public fKIdDepartamento?: number | null
  ) {}
}

export function getMunicipioIdentifier(municipio: IMunicipio): number | undefined {
  return municipio.id;
}
