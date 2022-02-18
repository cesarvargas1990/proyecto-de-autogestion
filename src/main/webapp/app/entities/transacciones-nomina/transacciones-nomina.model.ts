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
  pinPago?: number | null;
  fKDepartamentoDePago?: number | null;
  fKMunicipioDePago?: number | null;
  fKDepartamento?: number | null;
  fKMunicipio?: number | null;
  fKIdConvenio?: number | null;
  fKIdPrograma?: number | null;
  fechaDePago?: dayjs.Dayjs | null;
  valorGiro?: number | null;
  estado?: string | null;
  periodoPago?: string | null;
  motivoAnulacion?: string | null;
  fechaVigencia?: dayjs.Dayjs | null;
  fechaCargue?: dayjs.Dayjs | null;
  nota?: string | null;
  redPagadora?: string | null;
  observacionControl?: string | null;
  solicitudAutorizacion?: string | null;
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
    public pinPago?: number | null,
    public fKDepartamentoDePago?: number | null,
    public fKMunicipioDePago?: number | null,
    public fKDepartamento?: number | null,
    public fKMunicipio?: number | null,
    public fKIdConvenio?: number | null,
    public fKIdPrograma?: number | null,
    public fechaDePago?: dayjs.Dayjs | null,
    public valorGiro?: number | null,
    public estado?: string | null,
    public periodoPago?: string | null,
    public motivoAnulacion?: string | null,
    public fechaVigencia?: dayjs.Dayjs | null,
    public fechaCargue?: dayjs.Dayjs | null,
    public nota?: string | null,
    public redPagadora?: string | null,
    public observacionControl?: string | null,
    public solicitudAutorizacion?: string | null
  ) {}
}

export function getTransaccionesNominaIdentifier(transaccionesNomina: ITransaccionesNomina): number | undefined {
  return transaccionesNomina.id;
}
