package com.supergiros.portalautogestion.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Programas.
 */
@Entity
@Table(name = "programas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Programas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_programas")
    private Integer idProgramas;

    @Column(name = "name")
    private String name;

    @Column(name = "identificacion")
    private String identificacion;

    @Column(name = "f_k_convenio")
    private String fKConvenio;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Programas id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProgramas() {
        return this.idProgramas;
    }

    public Programas idProgramas(Integer idProgramas) {
        this.setIdProgramas(idProgramas);
        return this;
    }

    public void setIdProgramas(Integer idProgramas) {
        this.idProgramas = idProgramas;
    }

    public String getName() {
        return this.name;
    }

    public Programas name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentificacion() {
        return this.identificacion;
    }

    public Programas identificacion(String identificacion) {
        this.setIdentificacion(identificacion);
        return this;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getfKConvenio() {
        return this.fKConvenio;
    }

    public Programas fKConvenio(String fKConvenio) {
        this.setfKConvenio(fKConvenio);
        return this;
    }

    public void setfKConvenio(String fKConvenio) {
        this.fKConvenio = fKConvenio;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Programas)) {
            return false;
        }
        return id != null && id.equals(((Programas) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Programas{" +
            "id=" + getId() +
            ", idProgramas=" + getIdProgramas() +
            ", name='" + getName() + "'" +
            ", identificacion='" + getIdentificacion() + "'" +
            ", fKConvenio='" + getfKConvenio() + "'" +
            "}";
    }
}
