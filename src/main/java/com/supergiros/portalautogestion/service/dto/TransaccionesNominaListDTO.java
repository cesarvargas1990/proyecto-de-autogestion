package com.supergiros.portalautogestion.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class TransaccionesNominaListDTO implements Serializable {

    private static final long serialVersionUID = 92L;

    private Long id;

    private String tipoDocumentoBenef;

    private Long numeroDocumentoBenef;

    private String nombreUno;

    private String nombreDos;

    private String apellidoUno;

    private String apellidoDos;

    private String nombreUnoPago;

    private String nombreDosPago;

    private String apellidoUnoPago;

    private String apellidoDosPago;

    private LocalDate fechaPago;

    private String horaPago;

    private LocalDate fechaDePago;

    private String estado;

    private String periodoPago;

    private String motivoAnulacion;

    private LocalDate fechaVigencia;

    private LocalDate fechaCargue;

    private String nota;

    private String redPagadora;

    private String idNomina;

    private String solicitudAutorizacion;

    private String pinPago;

    private String fKDepartamentoDePago;

    private String fKMunicipioDePago;

    private String fKDepartamento;

    private String fKMunicipio;

    private String fKIdConvenio;

    private String fKIdPrograma;

    private String valorGiro;

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

    public String getNombreUno() {
        return nombreUno;
    }

    public void setNombreUno(String nombreUno) {
        this.nombreUno = nombreUno;
    }

    public String getNombreDos() {
        return nombreDos;
    }

    public void setNombreDos(String nombreDos) {
        this.nombreDos = nombreDos;
    }

    public String getApellidoUno() {
        return apellidoUno;
    }

    public void setApellidoUno(String apellidoUno) {
        this.apellidoUno = apellidoUno;
    }

    public String getApellidoDos() {
        return apellidoDos;
    }

    public void setApellidoDos(String apellidoDos) {
        this.apellidoDos = apellidoDos;
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

    public LocalDate getFechaDePago() {
        return fechaDePago;
    }

    public void setFechaDePago(LocalDate fechaDePago) {
        this.fechaDePago = fechaDePago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPeriodoPago() {
        return periodoPago;
    }

    public void setPeriodoPago(String periodoPago) {
        this.periodoPago = periodoPago;
    }

    public String getMotivoAnulacion() {
        return motivoAnulacion;
    }

    public void setMotivoAnulacion(String motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }

    public LocalDate getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(LocalDate fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public LocalDate getFechaCargue() {
        return fechaCargue;
    }

    public void setFechaCargue(LocalDate fechaCargue) {
        this.fechaCargue = fechaCargue;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
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

    public String getSolicitudAutorizacion() {
        return solicitudAutorizacion;
    }

    public void setSolicitudAutorizacion(String solicitudAutorizacion) {
        this.solicitudAutorizacion = solicitudAutorizacion;
    }

    public String getPinPago() {
        return pinPago;
    }

    public void setPinPago(String pinPago) {
        this.pinPago = pinPago;
    }

    public String getfKDepartamentoDePago() {
        return fKDepartamentoDePago;
    }

    public void setfKDepartamentoDePago(String fKDepartamentoDePago) {
        this.fKDepartamentoDePago = fKDepartamentoDePago;
    }

    public String getfKMunicipioDePago() {
        return fKMunicipioDePago;
    }

    public void setfKMunicipioDePago(String fKMunicipioDePago) {
        this.fKMunicipioDePago = fKMunicipioDePago;
    }

    public String getfKDepartamento() {
        return fKDepartamento;
    }

    public void setfKDepartamento(String fKDepartamento) {
        this.fKDepartamento = fKDepartamento;
    }

    public String getfKMunicipio() {
        return fKMunicipio;
    }

    public void setfKMunicipio(String fKMunicipio) {
        this.fKMunicipio = fKMunicipio;
    }

    public String getfKIdConvenio() {
        return fKIdConvenio;
    }

    public void setfKIdConvenio(String fKIdConvenio) {
        this.fKIdConvenio = fKIdConvenio;
    }

    public String getfKIdPrograma() {
        return fKIdPrograma;
    }

    public void setfKIdPrograma(String fKIdPrograma) {
        this.fKIdPrograma = fKIdPrograma;
    }

    public String getValorGiro() {
        return valorGiro;
    }

    public void setValorGiro(String valorGiro) {
        this.valorGiro = valorGiro;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
