package com.supergiros.portalautogestion.service.dto;

import javax.validation.constraints.*;

public class UserDepartamentoMunicipioDTO {

    // @NotNull
    private Long userId;

    // @NotNull
    private String[] municipioName;

    // @NotNull
    private String[] departamentoName;

    public UserDepartamentoMunicipioDTO() {}

    public UserDepartamentoMunicipioDTO(Long userId, String[] departamentoName, String[] municipioName) {
        this.userId = userId;
        this.municipioName = municipioName;
        this.departamentoName = departamentoName;
    }

    public Long getuserId() {
        return userId;
    }

    public void setuserId(Long userId) {
        this.userId = userId;
    }

    public String[] getmunicipioName() {
        return municipioName;
    }

    public void setmunicipioName(String[] municipioName) {
        this.municipioName = municipioName;
    }

    public String[] getDepartamentoName() {
        return departamentoName;
    }

    public void setDepartamentoName(String[] departamentoName) {
        this.departamentoName = departamentoName;
    }
}
