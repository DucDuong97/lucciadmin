package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ImgUrl.
 */
@Entity
@Table(name = "img_url")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ImgUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "path", nullable = false)
    private String path;

    @ManyToMany(mappedBy = "customerImgUrls")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ServiceItem> serviceItems = new HashSet<>();

    @ManyToMany(mappedBy = "treatmentImgUrls")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Treatment> treatments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String createAccessKey() {
        return path + "/" + name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ImgUrl name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public ImgUrl path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<ServiceItem> getServiceItems() {
        return serviceItems;
    }

    public ImgUrl serviceItems(Set<ServiceItem> serviceItems) {
        this.serviceItems = serviceItems;
        return this;
    }

    public ImgUrl addServiceItems(ServiceItem serviceItem) {
        this.serviceItems.add(serviceItem);
        serviceItem.getCustomerImgUrls().add(this);
        return this;
    }

    public ImgUrl removeServiceItems(ServiceItem serviceItem) {
        this.serviceItems.remove(serviceItem);
        serviceItem.getCustomerImgUrls().remove(this);
        return this;
    }

    public void setServiceItems(Set<ServiceItem> serviceItems) {
        this.serviceItems = serviceItems;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public ImgUrl treatments(Set<Treatment> treatments) {
        this.treatments = treatments;
        return this;
    }

    public ImgUrl addTreatment(Treatment treatment) {
        this.treatments.add(treatment);
        treatment.getTreatmentImgUrls().add(this);
        return this;
    }

    public ImgUrl removeTreatment(Treatment treatment) {
        this.treatments.remove(treatment);
        treatment.getTreatmentImgUrls().remove(this);
        return this;
    }

    public ImgUrl clearTreatments() {
        for (Treatment treatment : treatments) {
            removeTreatment(treatment);
        }
        return this;
    }

    public ImgUrl addTreatments() {
        for (Treatment treatment : treatments) {
            addTreatment(treatment);
        }
        return this;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImgUrl)) {
            return false;
        }
        return id != null && id.equals(((ImgUrl) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImgUrl{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }
}
