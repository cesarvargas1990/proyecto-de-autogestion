package com.supergiros.portalautogestion.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Municipio.
 */
@Entity
@Table(name = "municipio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_municipio")
    private Integer idMunicipio;

    @Column(name = "name")
    private String name;

    @Column(name = "cod_dane")
    private Integer codDane;

    @Column(name = "f_k_id_departamento")
    private Integer fKIdDepartamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Municipio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdMunicipio() {
        return this.idMunicipio;
    }

    public Municipio idMunicipio(Integer idMunicipio) {
        this.setIdMunicipio(idMunicipio);
        return this;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getName() {
        return this.name;
    }

    public Municipio name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCodDane() {
        return this.codDane;
    }

    public Municipio codDane(Integer codDane) {
        this.setCodDane(codDane);
        return this;
    }

    public void setCodDane(Integer codDane) {
        this.codDane = codDane;
    }

    public Integer getfKIdDepartamento() {
        return this.fKIdDepartamento;
    }

    public Municipio fKIdDepartamento(Integer fKIdDepartamento) {
        this.setfKIdDepartamento(fKIdDepartamento);
        return this;
    }

    public void setfKIdDepartamento(Integer fKIdDepartamento) {
        this.fKIdDepartamento = fKIdDepartamento;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Municipio)) {
            return false;
        }
        return id != null && id.equals(((Municipio) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Municipio{" +
            "id=" + getId() +
            ", idMunicipio=" + getIdMunicipio() +
            ", name='" + getName() + "'" +
            ", codDane=" + getCodDane() +
            ", fKIdDepartamento=" + getfKIdDepartamento() +
            "}";
    }
}
