package com.supergiros.portalautogestion.service.dto;

import java.sql.Time;
import java.util.Date;

public class TransaccionesNominaDTO {

    private int referencia_control;

    private int convenio;

    private int programa;

    private String nombre1;

    private String nombre2;

    private String apellido1;

    private String apellido2;

    private Date fechaDePago;

    private Time horaDePago;

    private int valorDePago;

    private int departamento;

    private int municipio;

    private String estado;

    private String motivoAnulacion;

    public int getReferencia_control() {
        return referencia_control;
    }

    public void setReferencia_control(int referencia_control) {
        this.referencia_control = referencia_control;
    }

    public int getConvenio() {
        return convenio;
    }

    public void setConvenio(int convenio) {
        this.convenio = convenio;
    }

    public int getPrograma() {
        return programa;
    }

    public void setPrograma(int programa) {
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

    public Date getFechaDePago() {
        return fechaDePago;
    }

    public void setFechaDePago(Date fechaDePago) {
        this.fechaDePago = fechaDePago;
    }

    public Time getHoraDePago() {
        return horaDePago;
    }

    public void setHoraDePago(Time horaDePago) {
        this.horaDePago = horaDePago;
    }

    public int getValorDePago() {
        return valorDePago;
    }

    public void setValorDePago(int valorDePago) {
        this.valorDePago = valorDePago;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public int getMunicipio() {
        return municipio;
    }

    public void setMunicipio(int municipio) {
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
}
