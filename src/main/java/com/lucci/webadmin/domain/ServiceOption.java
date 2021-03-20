package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ServiceOption.
 */
@Entity
@Table(name = "service_option")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "benefits", nullable = false)
    private String benefits;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties(value = "options", allowSetters = true)
    private ServiceItem service;

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

    public ServiceOption name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBenefits() {
        return benefits;
    }

    public ServiceOption benefits(String benefits) {
        this.benefits = benefits;
        return this;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public Integer getPrice() {
        return price;
    }

    public ServiceOption price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ServiceItem getService() {
        return service;
    }

    public ServiceOption service(ServiceItem serviceItem) {
        this.service = serviceItem;
        return this;
    }

    public void setService(ServiceItem serviceItem) {
        this.service = serviceItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOption)) {
            return false;
        }
        return id != null && id.equals(((ServiceOption) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceOption{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", benefits='" + getBenefits() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
