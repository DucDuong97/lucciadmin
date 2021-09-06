package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Potential.
 */
@Entity
@Table(name = "potential")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Potential implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "gender")
    private Boolean gender;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "potentials", allowSetters = true)
    private PricingCard service;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "potentials", allowSetters = true)
    private Branch branch;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Potential name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public Potential phone(Long phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Boolean isGender() {
        return gender;
    }

    public Potential gender(Boolean gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public PricingCard getService() {
        return service;
    }

    public Potential service(PricingCard pricingCard) {
        this.service = pricingCard;
        return this;
    }

    public void setService(PricingCard pricingCard) {
        this.service = pricingCard;
    }

    public Branch getBranch() {
        return branch;
    }

    public Potential branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Potential)) {
            return false;
        }
        return id != null && id.equals(((Potential) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Potential{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone=" + getPhone() +
            ", gender='" + isGender() + "'" +
            "}";
    }
}
