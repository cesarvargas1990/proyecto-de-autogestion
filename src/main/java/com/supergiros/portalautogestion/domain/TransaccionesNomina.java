package com.supergiros.portalautogestion.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TransaccionesNomina.
 */
@Entity
@Table(name = "transacciones_nomina")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TransaccionesNomina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo_documento_benef")
    private String tipoDocumentoBenef;

    @Column(name = "numero_documento_benef")
    private Integer numeroDocumentoBenef;

    @Column(name = "nombre_uno")
    private String nombreUno;

    @Column(name = "nombre_dos")
    private String nombreDos;

    @Column(name = "apellido_uno")
    private String apellidoUno;

    @Column(name = "apellido_dos")
    private String apellidoDos;

    @Column(name = "nombre_uno_pago")
    private String nombreUnoPago;

    @Column(name = "nombre_dos_pago")
    private String nombreDosPago;

    @Column(name = "apellido_uno_pago")
    private String apellidoUnoPago;

    @Column(name = "apellido_dos_pago")
    private String apellidoDosPago;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "hora_pago")
    private String horaPago;

    @Column(name = "pin_pago")
    private Integer pinPago;

    @Column(name = "f_k_departamento_de_pago")
    private Integer fKDepartamentoDePago;

    @Column(name = "f_k_municipio_de_pago")
    private Integer fKMunicipioDePago;

    @Column(name = "f_k_departamento")
    private Integer fKDepartamento;

    @Column(name = "f_k_municipio")
    private Integer fKMunicipio;

    @Column(name = "f_k_id_convenio")
    private Integer fKIdConvenio;

    @Column(name = "f_k_id_programa")
    private Integer fKIdPrograma;

    @Column(name = "fecha_de_pago")
    private LocalDate fechaDePago;

    @Column(name = "valor_giro")
    private Integer valorGiro;

    @Column(name = "estado")
    private String estado;

    @Column(name = "periodo_pago")
    private String periodoPago;

    @Column(name = "motivo_anulacion")
    private String motivoAnulacion;

    @Column(name = "fecha_vigencia")
    private LocalDate fechaVigencia;

    @Column(name = "fecha_cargue")
    private LocalDate fechaCargue;

    @Column(name = "nota")
    private String nota;

    @Column(name = "red_pagadora")
    private String redPagadora;

    @Column(name = "observacion_control")
    private String observacionControl;

    @Column(name = "solicitud_autorizacion")
    private String solicitudAutorizacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TransaccionesNomina id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDocumentoBenef() {
        return this.tipoDocumentoBenef;
    }

    public TransaccionesNomina tipoDocumentoBenef(String tipoDocumentoBenef) {
        this.setTipoDocumentoBenef(tipoDocumentoBenef);
        return this;
    }

    public void setTipoDocumentoBenef(String tipoDocumentoBenef) {
        this.tipoDocumentoBenef = tipoDocumentoBenef;
    }

    public Integer getNumeroDocumentoBenef() {
        return this.numeroDocumentoBenef;
    }

    public TransaccionesNomina numeroDocumentoBenef(Integer numeroDocumentoBenef) {
        this.setNumeroDocumentoBenef(numeroDocumentoBenef);
        return this;
    }

    public void setNumeroDocumentoBenef(Integer numeroDocumentoBenef) {
        this.numeroDocumentoBenef = numeroDocumentoBenef;
    }

    public String getNombreUno() {
        return this.nombreUno;
    }

    public TransaccionesNomina nombreUno(String nombreUno) {
        this.setNombreUno(nombreUno);
        return this;
    }

    public void setNombreUno(String nombreUno) {
        this.nombreUno = nombreUno;
    }

    public String getNombreDos() {
        return this.nombreDos;
    }

    public TransaccionesNomina nombreDos(String nombreDos) {
        this.setNombreDos(nombreDos);
        return this;
    }

    public void setNombreDos(String nombreDos) {
        this.nombreDos = nombreDos;
    }

    public String getApellidoUno() {
        return this.apellidoUno;
    }

    public TransaccionesNomina apellidoUno(String apellidoUno) {
        this.setApellidoUno(apellidoUno);
        return this;
    }

    public void setApellidoUno(String apellidoUno) {
        this.apellidoUno = apellidoUno;
    }

    public String getApellidoDos() {
        return this.apellidoDos;
    }

    public TransaccionesNomina apellidoDos(String apellidoDos) {
        this.setApellidoDos(apellidoDos);
        return this;
    }

    public void setApellidoDos(String apellidoDos) {
        this.apellidoDos = apellidoDos;
    }

    public String getNombreUnoPago() {
        return this.nombreUnoPago;
    }

    public TransaccionesNomina nombreUnoPago(String nombreUnoPago) {
        this.setNombreUnoPago(nombreUnoPago);
        return this;
    }

    public void setNombreUnoPago(String nombreUnoPago) {
        this.nombreUnoPago = nombreUnoPago;
    }

    public String getNombreDosPago() {
        return this.nombreDosPago;
    }

    public TransaccionesNomina nombreDosPago(String nombreDosPago) {
        this.setNombreDosPago(nombreDosPago);
        return this;
    }

    public void setNombreDosPago(String nombreDosPago) {
        this.nombreDosPago = nombreDosPago;
    }

    public String getApellidoUnoPago() {
        return this.apellidoUnoPago;
    }

    public TransaccionesNomina apellidoUnoPago(String apellidoUnoPago) {
        this.setApellidoUnoPago(apellidoUnoPago);
        return this;
    }

    public void setApellidoUnoPago(String apellidoUnoPago) {
        this.apellidoUnoPago = apellidoUnoPago;
    }

    public String getApellidoDosPago() {
        return this.apellidoDosPago;
    }

    public TransaccionesNomina apellidoDosPago(String apellidoDosPago) {
        this.setApellidoDosPago(apellidoDosPago);
        return this;
    }

    public void setApellidoDosPago(String apellidoDosPago) {
        this.apellidoDosPago = apellidoDosPago;
    }

    public LocalDate getFechaPago() {
        return this.fechaPago;
    }

    public TransaccionesNomina fechaPago(LocalDate fechaPago) {
        this.setFechaPago(fechaPago);
        return this;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getHoraPago() {
        return this.horaPago;
    }

    public TransaccionesNomina horaPago(String horaPago) {
        this.setHoraPago(horaPago);
        return this;
    }

    public void setHoraPago(String horaPago) {
        this.horaPago = horaPago;
    }

    public Integer getPinPago() {
        return this.pinPago;
    }

    public TransaccionesNomina pinPago(Integer pinPago) {
        this.setPinPago(pinPago);
        return this;
    }

    public void setPinPago(Integer pinPago) {
        this.pinPago = pinPago;
    }

    public Integer getfKDepartamentoDePago() {
        return this.fKDepartamentoDePago;
    }

    public TransaccionesNomina fKDepartamentoDePago(Integer fKDepartamentoDePago) {
        this.setfKDepartamentoDePago(fKDepartamentoDePago);
        return this;
    }

    public void setfKDepartamentoDePago(Integer fKDepartamentoDePago) {
        this.fKDepartamentoDePago = fKDepartamentoDePago;
    }

    public Integer getfKMunicipioDePago() {
        return this.fKMunicipioDePago;
    }

    public TransaccionesNomina fKMunicipioDePago(Integer fKMunicipioDePago) {
        this.setfKMunicipioDePago(fKMunicipioDePago);
        return this;
    }

    public void setfKMunicipioDePago(Integer fKMunicipioDePago) {
        this.fKMunicipioDePago = fKMunicipioDePago;
    }

    public Integer getfKDepartamento() {
        return this.fKDepartamento;
    }

    public TransaccionesNomina fKDepartamento(Integer fKDepartamento) {
        this.setfKDepartamento(fKDepartamento);
        return this;
    }

    public void setfKDepartamento(Integer fKDepartamento) {
        this.fKDepartamento = fKDepartamento;
    }

    public Integer getfKMunicipio() {
        return this.fKMunicipio;
    }

    public TransaccionesNomina fKMunicipio(Integer fKMunicipio) {
        this.setfKMunicipio(fKMunicipio);
        return this;
    }

    public void setfKMunicipio(Integer fKMunicipio) {
        this.fKMunicipio = fKMunicipio;
    }

    public Integer getfKIdConvenio() {
        return this.fKIdConvenio;
    }

    public TransaccionesNomina fKIdConvenio(Integer fKIdConvenio) {
        this.setfKIdConvenio(fKIdConvenio);
        return this;
    }

    public void setfKIdConvenio(Integer fKIdConvenio) {
        this.fKIdConvenio = fKIdConvenio;
    }

    public Integer getfKIdPrograma() {
        return this.fKIdPrograma;
    }

    public TransaccionesNomina fKIdPrograma(Integer fKIdPrograma) {
        this.setfKIdPrograma(fKIdPrograma);
        return this;
    }

    public void setfKIdPrograma(Integer fKIdPrograma) {
        this.fKIdPrograma = fKIdPrograma;
    }

    public LocalDate getFechaDePago() {
        return this.fechaDePago;
    }

    public TransaccionesNomina fechaDePago(LocalDate fechaDePago) {
        this.setFechaDePago(fechaDePago);
        return this;
    }

    public void setFechaDePago(LocalDate fechaDePago) {
        this.fechaDePago = fechaDePago;
    }

    public Integer getValorGiro() {
        return this.valorGiro;
    }

    public TransaccionesNomina valorGiro(Integer valorGiro) {
        this.setValorGiro(valorGiro);
        return this;
    }

    public void setValorGiro(Integer valorGiro) {
        this.valorGiro = valorGiro;
    }

    public String getEstado() {
        return this.estado;
    }

    public TransaccionesNomina estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPeriodoPago() {
        return this.periodoPago;
    }

    public TransaccionesNomina periodoPago(String periodoPago) {
        this.setPeriodoPago(periodoPago);
        return this;
    }

    public void setPeriodoPago(String periodoPago) {
        this.periodoPago = periodoPago;
    }

    public String getMotivoAnulacion() {
        return this.motivoAnulacion;
    }

    public TransaccionesNomina motivoAnulacion(String motivoAnulacion) {
        this.setMotivoAnulacion(motivoAnulacion);
        return this;
    }

    public void setMotivoAnulacion(String motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }

    public LocalDate getFechaVigencia() {
        return this.fechaVigencia;
    }

    public TransaccionesNomina fechaVigencia(LocalDate fechaVigencia) {
        this.setFechaVigencia(fechaVigencia);
        return this;
    }

    public void setFechaVigencia(LocalDate fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public LocalDate getFechaCargue() {
        return this.fechaCargue;
    }

    public TransaccionesNomina fechaCargue(LocalDate fechaCargue) {
        this.setFechaCargue(fechaCargue);
        return this;
    }

    public void setFechaCargue(LocalDate fechaCargue) {
        this.fechaCargue = fechaCargue;
    }

    public String getNota() {
        return this.nota;
    }

    public TransaccionesNomina nota(String nota) {
        this.setNota(nota);
        return this;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getRedPagadora() {
        return this.redPagadora;
    }

    public TransaccionesNomina redPagadora(String redPagadora) {
        this.setRedPagadora(redPagadora);
        return this;
    }

    public void setRedPagadora(String redPagadora) {
        this.redPagadora = redPagadora;
    }

    public String getObservacionControl() {
        return this.observacionControl;
    }

    public TransaccionesNomina observacionControl(String observacionControl) {
        this.setObservacionControl(observacionControl);
        return this;
    }

    public void setObservacionControl(String observacionControl) {
        this.observacionControl = observacionControl;
    }

    public String getSolicitudAutorizacion() {
        return this.solicitudAutorizacion;
    }

    public TransaccionesNomina solicitudAutorizacion(String solicitudAutorizacion) {
        this.setSolicitudAutorizacion(solicitudAutorizacion);
        return this;
    }

    public void setSolicitudAutorizacion(String solicitudAutorizacion) {
        this.solicitudAutorizacion = solicitudAutorizacion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransaccionesNomina)) {
            return false;
        }
        return id != null && id.equals(((TransaccionesNomina) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransaccionesNomina{" +
            "id=" + getId() +
            ", tipoDocumentoBenef='" + getTipoDocumentoBenef() + "'" +
            ", numeroDocumentoBenef=" + getNumeroDocumentoBenef() +
            ", nombreUno='" + getNombreUno() + "'" +
            ", nombreDos='" + getNombreDos() + "'" +
            ", apellidoUno='" + getApellidoUno() + "'" +
            ", apellidoDos='" + getApellidoDos() + "'" +
            ", nombreUnoPago='" + getNombreUnoPago() + "'" +
            ", nombreDosPago='" + getNombreDosPago() + "'" +
            ", apellidoUnoPago='" + getApellidoUnoPago() + "'" +
            ", apellidoDosPago='" + getApellidoDosPago() + "'" +
            ", fechaPago='" + getFechaPago() + "'" +
            ", horaPago='" + getHoraPago() + "'" +
            ", pinPago=" + getPinPago() +
            ", fKDepartamentoDePago=" + getfKDepartamentoDePago() +
            ", fKMunicipioDePago=" + getfKMunicipioDePago() +
            ", fKDepartamento=" + getfKDepartamento() +
            ", fKMunicipio=" + getfKMunicipio() +
            ", fKIdConvenio=" + getfKIdConvenio() +
            ", fKIdPrograma=" + getfKIdPrograma() +
            ", fechaDePago='" + getFechaDePago() + "'" +
            ", valorGiro=" + getValorGiro() +
            ", estado='" + getEstado() + "'" +
            ", periodoPago='" + getPeriodoPago() + "'" +
            ", motivoAnulacion='" + getMotivoAnulacion() + "'" +
            ", fechaVigencia='" + getFechaVigencia() + "'" +
            ", fechaCargue='" + getFechaCargue() + "'" +
            ", nota='" + getNota() + "'" +
            ", redPagadora='" + getRedPagadora() + "'" +
            ", observacionControl='" + getObservacionControl() + "'" +
            ", solicitudAutorizacion='" + getSolicitudAutorizacion() + "'" +
            "}";
    }
}
