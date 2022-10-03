package com.supergiros.portalautogestion.service.dto;

import com.supergiros.portalautogestion.domain.TransaccionesNomina;
import java.util.List;

public class TransaccionesMSDTO {

    String document;

    List<String> NIT;

    List<String> municipalities;

    String idNomina;

    public TransaccionesMSDTO() {
        super();
    }

    public TransaccionesMSDTO(String document, List<String> NIT, List<String> municipalities, String idNomina) {
        this.document = document;
        this.NIT = NIT;
        this.municipalities = municipalities;
        this.idNomina = idNomina;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public List<String> getNIT() {
        return NIT;
    }

    public void setNIT(List<String> NIT) {
        this.NIT = NIT;
    }

    public List<String> getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(List<String> municipalities) {
        this.municipalities = municipalities;
    }

    public String getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(String idNomina) {
        this.idNomina = idNomina;
    }
}
