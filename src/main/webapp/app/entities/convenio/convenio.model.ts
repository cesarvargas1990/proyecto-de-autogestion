export interface IConvenio {
  id?: number;
  idConvenio?: number | null;
  name?: string | null;
  identificacion?: string | null;
}

export class Convenio implements IConvenio {
  constructor(public id?: number, public idConvenio?: number | null, public name?: string | null, public identificacion?: string | null) {}
}

export function getConvenioIdentifier(convenio: IConvenio): number | undefined {
  return convenio.id;
}
