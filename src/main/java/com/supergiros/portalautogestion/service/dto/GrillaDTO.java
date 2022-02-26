package com.supergiros.portalautogestion.service.dto;

import com.supergiros.portalautogestion.config.Constants;
import com.supergiros.portalautogestion.domain.Authority;
import com.supergiros.portalautogestion.domain.User;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;

public class GrillaDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String documentType;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    private Long convenio;

    @NotNull
    private Long programa;

    @NotNull
    private Long departamento;

    @NotNull
    private Long municipio;

    @NotNull
    private Set<String> authorities;

    public GrillaDTO() {
        // Empty constructor needed for Jackson.
    }

    public GrillaDTO(User user) {
        this.id = user.getId();
        this.documentType = user.getDocumentType();
        this.login = user.getLogin();
        this.convenio = user.getConvenio();
        this.programa = user.getPrograma();
        this.departamento = user.getDepartamento();
        this.municipio = user.getMunicipio();
        this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Long getConvenio() {
        return convenio;
    }

    public void setConvenio(Long convenio) {
        this.convenio = convenio;
    }

    public Long getPrograma() {
        return programa;
    }

    public void setPrograma(Long programa) {
        this.programa = programa;
    }

    public Long getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Long departamento) {
        this.departamento = departamento;
    }

    public Long getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Long municipio) {
        this.municipio = municipio;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
