import dayjs from 'dayjs/esm';

export interface ITransaccionesNomina {
  id?: number;
  tipoDocumentoBenef?: string | null;
  numeroDocumentoBenef?: number | null;
  nombreUno?: string | null;
  nombreDos?: string | null;
  apellidoUno?: string | null;
  apellidoDos?: string | null;
  nombreUnoPago?: string | null;
  nombreDosPago?: string | null;
  apellidoUnoPago?: string | null;
  apellidoDosPago?: string | null;
  fechaPago?: dayjs.Dayjs | null;
  horaPago?: string | null;
  fechaDePago?: dayjs.Dayjs | null;
  estado?: string | null;
  periodoPago?: string | null;
  motivoAnulacion?: string | null;
  fechaVigencia?: dayjs.Dayjs | null;
  fechaCargue?: dayjs.Dayjs | null;
  nota?: string | null;
  redPagadora?: string | null;
  observacionControl?: string | null;
  solicitudAutorizacion?: string | null;
  pinPago?: string | null;
  fKDepartamentoDePago?: string | null;
  fKMunicipioDePago?: string | null;
  fKDepartamento?: string | null;
  fKMunicipio?: string | null;
  fKIdConvenio?: string | null;
  fKIdPrograma?: string | null;
  valorGiro?: string | null;
  idNomina?: string | null;
}

export class TransaccionesNomina implements ITransaccionesNomina {
  constructor(
    public id?: number,
    public tipoDocumentoBenef?: string | null,
    public numeroDocumentoBenef?: number | null,
    public nombreUno?: string | null,
    public nombreDos?: string | null,
    public apellidoUno?: string | null,
    public apellidoDos?: string | null,
    public nombreUnoPago?: string | null,
    public nombreDosPago?: string | null,
    public apellidoUnoPago?: string | null,
    public apellidoDosPago?: string | null,
    public fechaPago?: dayjs.Dayjs | null,
    public horaPago?: string | null,
    public fechaDePago?: dayjs.Dayjs | null,
    public estado?: string | null,
    public periodoPago?: string | null,
    public motivoAnulacion?: string | null,
    public fechaVigencia?: dayjs.Dayjs | null,
    public fechaCargue?: dayjs.Dayjs | null,
    public nota?: string | null,
    public redPagadora?: string | null,
    public observacionControl?: string | null,
    public solicitudAutorizacion?: string | null,
    public pinPago?: string | null,
    public fKDepartamentoDePago?: string | null,
    public fKMunicipioDePago?: string | null,
    public fKDepartamento?: string | null,
    public fKMunicipio?: string | null,
    public fKIdConvenio?: string | null,
    public fKIdPrograma?: string | null,
    public valorGiro?: string | null,
    public idNomina?: string | null
  ) {}
}

export function getTransaccionesNominaIdentifier(transaccionesNomina: ITransaccionesNomina): number | undefined {
  return transaccionesNomina.id;
}
