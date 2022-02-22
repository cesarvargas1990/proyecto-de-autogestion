package com.supergiros.portalautogestion.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Departamentos.
 */
@Entity
@Table(name = "departamentos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Departamentos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cod_dane")
    private Integer codDane;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Departamentos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Departamentos name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCodDane() {
        return this.codDane;
    }

    public Departamentos codDane(Integer codDane) {
        this.setCodDane(codDane);
        return this;
    }

    public void setCodDane(Integer codDane) {
        this.codDane = codDane;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departamentos)) {
            return false;
        }
        return id != null && id.equals(((Departamentos) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Departamentos{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", codDane=" + getCodDane() +
            "}";
    }
}
