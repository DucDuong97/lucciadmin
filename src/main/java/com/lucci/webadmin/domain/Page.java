package com.lucci.webadmin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.lucci.webadmin.domain.enumeration.SubPage;

/**
 * A Page.
 */
@Entity
@Table(name = "page")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "page", nullable = false, unique = true)
    private SubPage page;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubPage getPage() {
        return page;
    }

    public Page page(SubPage page) {
        this.page = page;
        return this;
    }

    public void setPage(SubPage page) {
        this.page = page;
    }

    public String getDescription() {
        return description;
    }

    public Page description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Page)) {
            return false;
        }
        return id != null && id.equals(((Page) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Page{" +
            "id=" + getId() +
            ", page='" + getPage() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
