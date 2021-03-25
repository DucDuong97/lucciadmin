package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A GetMaterial.
 */
@Entity
@Table(name = "get_material")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GetMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @OneToOne
    @JoinColumn(unique = true)
    private Material material;

    @ManyToOne
    @JsonIgnoreProperties(value = "getMaterials", allowSetters = true)
    private Receptionist receptionist;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public GetMaterial date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Material getMaterial() {
        return material;
    }

    public GetMaterial material(Material material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Receptionist getReceptionist() {
        return receptionist;
    }

    public GetMaterial receptionist(Receptionist receptionist) {
        this.receptionist = receptionist;
        return this;
    }

    public void setReceptionist(Receptionist receptionist) {
        this.receptionist = receptionist;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetMaterial)) {
            return false;
        }
        return id != null && id.equals(((GetMaterial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GetMaterial{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
