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

    @OneToMany(mappedBy = "pricingCard")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PricingContent> pricingContents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "pricingCards", allowSetters = true)
    private ServiceItem serviceItem;

    @OneToOne
    @JoinColumn(unique = true)
    private ImgUrl imgUrl;

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

    public Set<PricingContent> getPricingContents() {
        return pricingContents;
    }

    public PricingCard pricingContents(Set<PricingContent> pricingContents) {
        this.pricingContents = pricingContents;
        return this;
    }

    public PricingCard addPricingContent(PricingContent pricingContent) {
        this.pricingContents.add(pricingContent);
        pricingContent.setPricingCard(this);
        return this;
    }

    public PricingCard removePricingContent(PricingContent pricingContent) {
        this.pricingContents.remove(pricingContent);
        pricingContent.setPricingCard(null);
        return this;
    }

    public void setPricingContents(Set<PricingContent> pricingContents) {
        this.pricingContents = pricingContents;
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

    public ImgUrl getImgUrl() {
        return imgUrl;
    }

    public PricingCard imgUrl(ImgUrl imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(ImgUrl imgUrl) {
        this.imgUrl = imgUrl;
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
