package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Blog.
 */
@Entity
@Table(name = "blog")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "publish_date", nullable = false)
    private Instant publishDate;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private ImgUrl titleImgUrl;

    @ManyToOne
    private ServiceItem serviceItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Blog title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getPublishDate() {
        return publishDate;
    }

    public Blog publishDate(Instant publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public void setPublishDate(Instant publishDate) {
        this.publishDate = publishDate;
    }

    public String getContent() {
        return content;
    }

    public Blog content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public Blog description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImgUrl getTitleImgUrl() {
        return titleImgUrl;
    }

    public Blog titleImgUrl(ImgUrl imgUrl) {
        this.titleImgUrl = imgUrl;
        return this;
    }

    public void setTitleImgUrl(ImgUrl imgUrl) {
        this.titleImgUrl = imgUrl;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public Blog serviceItem(ServiceItem serviceItem) {
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
        if (!(o instanceof Blog)) {
            return false;
        }
        return id != null && id.equals(((Blog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Blog{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", publishDate='" + getPublishDate() + "'" +
            ", content='" + getContent() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
