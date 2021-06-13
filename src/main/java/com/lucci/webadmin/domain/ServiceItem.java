package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    @JsonIgnoreProperties(value = "serviceItem", allowSetters = true)
    private ImgUrl imgUrl;

    @OneToMany(mappedBy = "service")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ServiceOption> options = new HashSet<>();

    @OneToMany(mappedBy = "serviceItem", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Process> processes = new HashSet<>();

    @OneToMany(mappedBy = "serviceItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Blog> relatedBlogs = new HashSet<>();

    @OneToMany(mappedBy = "serviceItem", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<PricingCard> pricingCards = new HashSet<>();

    @OneToMany(mappedBy = "serviceItem", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "serviceItem", allowSetters = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Video> relatedVideos = new HashSet<>();

    @OneToMany(mappedBy = "serviceItem", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "serviceItem", allowSetters = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ImgUrl> customerImgUrls = new HashSet<>();

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

    public ImgUrl getImgUrl() {
        return imgUrl;
    }

    public ServiceItem imgUrl(ImgUrl imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(ImgUrl imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<ServiceOption> getOptions() {
        return options;
    }

    public ServiceItem options(Set<ServiceOption> serviceOptions) {
        this.options = serviceOptions;
        return this;
    }

    public ServiceItem addOptions(ServiceOption serviceOption) {
        this.options.add(serviceOption);
        serviceOption.setService(this);
        return this;
    }

    public ServiceItem removeOptions(ServiceOption serviceOption) {
        this.options.remove(serviceOption);
        serviceOption.setService(null);
        return this;
    }

    public void setOptions(Set<ServiceOption> serviceOptions) {
        this.options = serviceOptions;
    }

    public Set<Process> getProcesses() {
        return processes;
    }

    public ServiceItem processes(Set<Process> processes) {
        this.processes = processes;
        return this;
    }

    public ServiceItem addProcesses(Process process) {
        this.processes.add(process);
        process.setServiceItem(this);
        return this;
    }

    public ServiceItem removeProcesses(Process process) {
        this.processes.remove(process);
        process.setServiceItem(null);
        return this;
    }

    public void setProcesses(Set<Process> processes) {
        this.processes = processes;
    }

    public Set<Blog> getRelatedBlogs() {
        return relatedBlogs;
    }

    public ServiceItem relatedBlogs(Set<Blog> blogs) {
        this.relatedBlogs = blogs;
        return this;
    }

    public ServiceItem addRelatedBlogs(Blog blog) {
        this.relatedBlogs.add(blog);
        blog.setServiceItem(this);
        return this;
    }

    public ServiceItem removeRelatedBlogs(Blog blog) {
        this.relatedBlogs.remove(blog);
        blog.setServiceItem(null);
        return this;
    }

    public void setRelatedBlogs(Set<Blog> blogs) {
        this.relatedBlogs = blogs;
    }

    public Set<Video> getRelatedVideos() {
        return relatedVideos;
    }

    public ServiceItem relatedVideos(Set<Video> videos) {
        this.relatedVideos = videos;
        return this;
    }

    public ServiceItem addRelatedVideos(Video video) {
        this.relatedVideos.add(video);
        video.setServiceItem(this);
        return this;
    }

    public ServiceItem removeRelatedVideos(Video video) {
        this.relatedVideos.remove(video);
        video.setServiceItem(null);
        return this;
    }

    public void setRelatedVideos(Set<Video> videos) {
        this.relatedVideos = videos;
    }

    public Set<ImgUrl> getCustomerImgUrls() {
        return customerImgUrls;
    }

    public ServiceItem customerImgUrls(Set<ImgUrl> imgUrls) {
        this.customerImgUrls = imgUrls;
        return this;
    }

    public ServiceItem addCustomerImgUrls(ImgUrl imgUrl) {
        this.customerImgUrls.add(imgUrl);
        imgUrl.setServiceItem(this);
        return this;
    }

    public ServiceItem removeCustomerImgUrls(ImgUrl imgUrl) {
        this.customerImgUrls.remove(imgUrl);
        imgUrl.setServiceItem(null);
        return this;
    }

    public void setCustomerImgUrls(Set<ImgUrl> imgUrls) {
        this.customerImgUrls = imgUrls;
    }

    public Set<PricingCard> getPricingCards() {
        return pricingCards;
    }

    public ServiceItem pricingCards(Set<PricingCard> pricingCards) {
        this.pricingCards = pricingCards;
        return this;
    }

    public ServiceItem addPricingCards(PricingCard pricingCard) {
        this.pricingCards.add(pricingCard);
        pricingCard.setServiceItem(this);
        return this;
    }

    public ServiceItem removePricingCards(PricingCard pricingCard) {
        this.pricingCards.remove(pricingCard);
        pricingCard.setServiceItem(null);
        return this;
    }

    public void setPricingCards(Set<PricingCard> pricingCards) {
        this.pricingCards = pricingCards;
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
            "}";
    }
}
