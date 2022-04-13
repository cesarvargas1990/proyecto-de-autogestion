package com.supergiros.portalautogestion.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "jhi_user_location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDepartamentoMunicipio {

    @Id
    private Long id;

    @NotNull
    @Builder.Default
    private Long user_id;

    @NotNull
    @Size(max = 50)
    private String municipio_name;

    @Size(max = 50)
    private String departamento_name;

    @Override
    public String toString() {
        return (
            "UserDepartamentoMunicipio [departamento_name=" +
            departamento_name +
            ", municipio_name=" +
            municipio_name +
            ", user_id=" +
            user_id +
            "]"
        );
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getMunicipio_name() {
        return municipio_name;
    }

    public void setMunicipio_name(String municipio_name) {
        this.municipio_name = municipio_name;
    }

    public String getDepartamento_name() {
        return departamento_name;
    }

    public void setDepartamento_name(String departamento_name) {
        this.departamento_name = departamento_name;
    }
}
