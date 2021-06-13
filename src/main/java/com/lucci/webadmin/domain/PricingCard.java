package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PricingCard.
 */
@Entity
@Table(name = "pricing_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = "serviceItem", allowSetters = true)
public class PricingCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "pricingCard", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PricingContent> contents = new HashSet<>();

    @ManyToOne
    private ServiceItem serviceItem;

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

    public PricingCard name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public PricingCard price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Set<PricingContent> getContents() {
        return contents;
    }

    public PricingCard contents(Set<PricingContent> pricingContents) {
        this.contents = pricingContents;
        return this;
    }

    public PricingCard addContents(PricingContent pricingContent) {
        this.contents.add(pricingContent);
        pricingContent.setPricingCard(this);
        return this;
    }

    public PricingCard removeContents(PricingContent pricingContent) {
        this.contents.remove(pricingContent);
        pricingContent.setPricingCard(null);
        return this;
    }

    public void setContents(Set<PricingContent> pricingContents) {
        this.contents = pricingContents;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public PricingCard serviceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
        return this;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PricingCard)) {
            return false;
        }
        return id != null && id.equals(((PricingCard) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PricingCard{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
