package com.lucci.webadmin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceItem.
 */
@Entity
@Table(name = "service_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 30)
    @Pattern(regexp = "^[A-Za-z]*$")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Pattern(regexp = "^/images/[a-zA-Z0-9_\\-]*\\.[a-zA-Z]*$")
    @Column(name = "img_url")
    private String imgUrl;

    @Size(max = 3)
    @OneToMany(mappedBy = "service")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ServiceOption> options = new HashSet<>();

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

    public ServiceItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ServiceItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public ServiceItem imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<ServiceOption> getOptions() {
        return options;
    }

    public ServiceItem options(Set<ServiceOption> serviceOptions) {
        this.options = serviceOptions;
        return this;
    }

    public ServiceItem addOption(ServiceOption serviceOption) {
        this.options.add(serviceOption);
        serviceOption.setService(this);
        return this;
    }

    public ServiceItem removeOption(ServiceOption serviceOption) {
        this.options.remove(serviceOption);
        serviceOption.setService(null);
        return this;
    }

    public void setOptions(Set<ServiceOption> serviceOptions) {
        this.options = serviceOptions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceItem)) {
            return false;
        }
        return id != null && id.equals(((ServiceItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            "}";
    }
}
