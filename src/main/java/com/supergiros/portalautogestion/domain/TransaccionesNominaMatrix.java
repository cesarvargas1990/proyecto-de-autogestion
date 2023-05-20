package com.supergiros.portalautogestion.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

/**
 * A TransaccionesNominaMatrix.
 */
@Entity
@Table(name = "transacciones_nomina_matrix")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TransaccionesNominaMatrix implements Serializable {

    private static final long serialVersionUID = 2L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDocumentoBenef() {
        return tipoDocumentoBenef;
    }

    public void setTipoDocumentoBenef(String tipoDocumentoBenef) {
        this.tipoDocumentoBenef = tipoDocumentoBenef;
    }

    public Long getNumeroDocumentoBenef() {
        return numeroDocumentoBenef;
    }

    public void setNumeroDocumentoBenef(Long numeroDocumentoBenef) {
        this.numeroDocumentoBenef = numeroDocumentoBenef;
    }

    public String getNombreUnoPago() {
        return nombreUnoPago;
    }

    public void setNombreUnoPago(String nombreUnoPago) {
        this.nombreUnoPago = nombreUnoPago;
    }

    public String getNombreDosPago() {
        return nombreDosPago;
    }

    public void setNombreDosPago(String nombreDosPago) {
        this.nombreDosPago = nombreDosPago;
    }

    public String getApellidoUnoPago() {
        return apellidoUnoPago;
    }

    public void setApellidoUnoPago(String apellidoUnoPago) {
        this.apellidoUnoPago = apellidoUnoPago;
    }

    public String getApellidoDosPago() {
        return apellidoDosPago;
    }

    public void setApellidoDosPago(String apellidoDosPago) {
        this.apellidoDosPago = apellidoDosPago;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getHoraPago() {
        return horaPago;
    }

    public void setHoraPago(String horaPago) {
        this.horaPago = horaPago;
    }

    public String getPinPagoRedExterna() {
        return pinPagoRedExterna;
    }

    public void setPinPagoRedExterna(String pinPagoRedExterna) {
        this.pinPagoRedExterna = pinPagoRedExterna;
    }

    public String getPinPagRedExtHomologa() {
        return pinPagRedExtHomologa;
    }

    public void setPinPagRedExtHomologa(String pinPagRedExtHomologa) {
        this.pinPagRedExtHomologa = pinPagRedExtHomologa;
    }

    public String getPinColocacionSg() {
        return pinColocacionSg;
    }

    public void setPinColocacionSg(String pinColocacionSg) {
        this.pinColocacionSg = pinColocacionSg;
    }

    public String getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(String idConvenio) {
        this.idConvenio = idConvenio;
    }

    public String getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(String idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getValorGiroPago() {
        return valorGiroPago;
    }

    public void setValorGiroPago(String valorGiroPago) {
        this.valorGiroPago = valorGiroPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivoAnulacion() {
        return motivoAnulacion;
    }

    public void setMotivoAnulacion(String motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }

    public String getRedPagadora() {
        return redPagadora;
    }

    public void setRedPagadora(String redPagadora) {
        this.redPagadora = redPagadora;
    }

    public String getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(String idNomina) {
        this.idNomina = idNomina;
    }

    public String getAgenciaPlanificaPago() {
        return agenciaPlanificaPago;
    }

    public void setAgenciaPlanificaPago(String agenciaPlanificaPago) {
        this.agenciaPlanificaPago = agenciaPlanificaPago;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo_documento_benef")
    private String tipoDocumentoBenef;

    @Column(name = "numero_documento_benef")
    private Long numeroDocumentoBenef;

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

    @Column(name = "pin_pago_red_externa")
    private String pinPagoRedExterna;

    @Column(name = "pin_pag_redext_homologa")
    private String pinPagRedExtHomologa;

    @Column(name = "pin_colocacion_sg")
    private String pinColocacionSg;

    @Column(name = "id_convenio")
    private String idConvenio;

    @Column(name = "id_programa")
    private String idPrograma;

    @Column(name = "valor_giro_pago")
    private String valorGiroPago;

    @Column(name = "estado")
    private String estado;

    @Column(name = "motivo_anulacion")
    private String motivoAnulacion;

    @Column(name = "red_pagadora")
    private String redPagadora;

    @Column(name = "id_nomina")
    private String idNomina;

    @Column(name = "agencia_planifica_pago")
    private String agenciaPlanificaPago;


    public static TransaccionesNomina toTransaccionesNomina(TransaccionesNominaMatrix transaccionesNominaMatrix) {
        TransaccionesNomina transaccionesNomina = new TransaccionesNomina();
        transaccionesNomina.setId(transaccionesNominaMatrix.getId());
        transaccionesNomina.setTipoDocumentoBenef(transaccionesNominaMatrix.getTipoDocumentoBenef());
        transaccionesNomina.setNumeroDocumentoBenef(transaccionesNominaMatrix.getNumeroDocumentoBenef());
        transaccionesNomina.setNombreUnoPago(transaccionesNominaMatrix.getNombreUnoPago());
        transaccionesNomina.setNombreDosPago(transaccionesNominaMatrix.getNombreDosPago());
        transaccionesNomina.setApellidoUnoPago(transaccionesNominaMatrix.getApellidoUnoPago());
        transaccionesNomina.setApellidoDosPago(transaccionesNominaMatrix.getApellidoDosPago());
        transaccionesNomina.setFechaPago(transaccionesNominaMatrix.getFechaPago());
        transaccionesNomina.setHoraPago(transaccionesNominaMatrix.getHoraPago());
        transaccionesNomina.setFechaDePago(transaccionesNominaMatrix.getFechaPago());
        transaccionesNomina.setEstado(transaccionesNominaMatrix.getEstado());
        //transaccionesNomina.setPeriodoPago(transaccionesNominaMatrix.getPeri());
        transaccionesNomina.setMotivoAnulacion(transaccionesNominaMatrix.getMotivoAnulacion());
        transaccionesNomina.setRedPagadora(transaccionesNominaMatrix.getRedPagadora());
        transaccionesNomina.setPinPago(transaccionesNominaMatrix.getPinPagoRedExterna());
        transaccionesNomina.setfKIdConvenio(transaccionesNominaMatrix.getIdConvenio());
        transaccionesNomina.setfKIdPrograma(transaccionesNominaMatrix.getIdPrograma());
        transaccionesNomina.setValorGiro(transaccionesNominaMatrix.getValorGiroPago());
        transaccionesNomina.setAgenciaOficinaNombre(transaccionesNominaMatrix.getAgenciaPlanificaPago());

        return transaccionesNomina;
    }
}
