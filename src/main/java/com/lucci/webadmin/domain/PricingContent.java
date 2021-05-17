package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PricingContent.
 */
@Entity
@Table(name = "pricing_content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PricingContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "pro", nullable = false)
    private Boolean pro;

    @ManyToOne
    @JsonIgnoreProperties(value = "contents", allowSetters = true)
    private PricingCard pricingCard;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public PricingContent content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isPro() {
        return pro;
    }

    public PricingContent pro(Boolean pro) {
        this.pro = pro;
        return this;
    }

    public void setPro(Boolean pro) {
        this.pro = pro;
    }

    public PricingCard getPricingCard() {
        return pricingCard;
    }

    public PricingContent pricingCard(PricingCard pricingCard) {
        this.pricingCard = pricingCard;
        return this;
    }

    public void setPricingCard(PricingCard pricingCard) {
        this.pricingCard = pricingCard;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PricingContent)) {
            return false;
        }
        return id != null && id.equals(((PricingContent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PricingContent{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", pro='" + isPro() + "'" +
            "}";
    }
}
