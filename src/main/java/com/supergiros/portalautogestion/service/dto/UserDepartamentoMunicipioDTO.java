package com.supergiros.portalautogestion.service.dto;

import javax.validation.constraints.*;

public class UserDepartamentoMunicipioDTO {

    //@NotNull
    private Long userId;

    //@NotNull
    private String[] municipioName;

    public UserDepartamentoMunicipioDTO() {}

    public UserDepartamentoMunicipioDTO(Long userId, String[] municipioName) {
        this.userId = userId;
        this.municipioName = municipioName;
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
}
