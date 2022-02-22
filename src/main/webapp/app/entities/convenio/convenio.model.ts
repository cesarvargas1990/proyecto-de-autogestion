export interface IConvenio {
  id?: number;
  name?: string | null;
  identificacion?: string | null;
}

export class Convenio implements IConvenio {
  constructor(public id?: number, public name?: string | null, public identificacion?: string | null) {}
}

export function getConvenioIdentifier(convenio: IConvenio): number | undefined {
  return convenio.id;
}
