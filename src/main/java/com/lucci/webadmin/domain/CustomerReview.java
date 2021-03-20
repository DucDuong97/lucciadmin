package com.lucci.webadmin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CustomerReview.
 */
@Entity
@Table(name = "customer_review")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Size(max = 30)
    @Column(name = "customer_title")
    private String customerTitle;

    @NotNull
    @Column(name = "comment", nullable = false)
    private String comment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public CustomerReview customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTitle() {
        return customerTitle;
    }

    public CustomerReview customerTitle(String customerTitle) {
        this.customerTitle = customerTitle;
        return this;
    }

    public void setCustomerTitle(String customerTitle) {
        this.customerTitle = customerTitle;
    }

    public String getComment() {
        return comment;
    }

    public CustomerReview comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerReview)) {
            return false;
        }
        return id != null && id.equals(((CustomerReview) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerReview{" +
            "id=" + getId() +
            ", customerName='" + getCustomerName() + "'" +
            ", customerTitle='" + getCustomerTitle() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
