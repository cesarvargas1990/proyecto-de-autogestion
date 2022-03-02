package com.supergiros.portalautogestion.service.dto;

import java.time.LocalDate;

public class TransaccionesNominaDTO {

    private String tipoDocumento;

    private int identificacion;

    private String referencia_control;

    private String periodoPago;

    private String convenio;

    private String programa;

    private String nombre1;

    private String nombre2;

    private String apellido1;

    private String apellido2;

    private LocalDate fechaDePago;

    private String horaDePago;

    private String valorDePago;

    private String departamento;

    private String municipio;

    private String estado;

    private String motivoAnulacion;

    public String getReferencia_control() {
        return referencia_control;
    }

    public void setReferencia_control(String referencia_control) {
        this.referencia_control = referencia_control;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public LocalDate getFechaDePago() {
        return fechaDePago;
    }

    public void setFechaDePago(LocalDate fechaDePago) {
        this.fechaDePago = fechaDePago;
    }

    public String getHoraDePago() {
        return horaDePago;
    }

    public void setHoraDePago(String horaDePago) {
        this.horaDePago = horaDePago;
    }

    public String getValorDePago() {
        return valorDePago;
    }

    public void setValorDePago(String valorDePago) {
        this.valorDePago = valorDePago;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
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

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getPeriodoPago() {
        return periodoPago;
    }

    public void setPeriodoPago(String periodoPago) {
        this.periodoPago = periodoPago;
    }
}
