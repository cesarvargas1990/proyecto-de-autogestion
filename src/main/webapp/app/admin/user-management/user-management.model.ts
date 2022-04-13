export interface IUser {
  id?: number;
  login?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;

  convenio?: number;
  convenioName?: string;
}

export class User implements IUser {
  constructor(
    public id?: number,
    public login?: string,
    public documentType?: string,
    public loginSearch?: string,
    public documentTypeSearch?: string,
    public celphone?: string,
    public convenio?: number,
    public convenioName?: string,
    public programa?: number,
    public programaName?: string,
    public departamento?: number,
    public departamentos?: string,
    public municipio?: number,
    public departamentoName?: string[],
    public municipioName?: string,
    public isMunicipios?: string,

    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date
  ) {}
}

export class udmModel {
  constructor(
    //public id?: number,
    public userId?: number,
    //public municipioName?: string,
    // public departamento?: number,
    // public municipio?: number,
    public departamentoName?: string[],
    public municipioName?: string[]
  ) {}
}

export class Grilla implements IUser {
  constructor(
    public id?: number,
    public documentType?: string,
    public login?: string,

    public convenio?: number,
    public programa?: number,
    public departamento?: number,
    public municipio?: number,

    public authorities?: string[]
  ) {}
}
