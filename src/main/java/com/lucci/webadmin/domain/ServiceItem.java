package com.lucci.webadmin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

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

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(unique = true)
    private ImgUrl icon;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "service_item_customer_img_urls",
               joinColumns = @JoinColumn(name = "service_item_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "customer_img_urls_id", referencedColumnName = "id"))
    private Set<ImgUrl> customerImgUrls = new HashSet<>();

    @OneToMany(mappedBy = "serviceItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Process> processes = new HashSet<>();

    @OneToMany(mappedBy = "serviceItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Blog> relatedBlogs = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "service_item_related_videos",
               joinColumns = @JoinColumn(name = "service_item_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "related_videos_id", referencedColumnName = "id"))
    private Set<Video> relatedVideos = new HashSet<>();

    @OneToMany(mappedBy = "serviceItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PricingCard> pricingCards = new HashSet<>();

    @PreRemove
    private void preRemove() {
        customerImgUrls.clear();
        relatedVideos.clear();
        processes.forEach(item -> item.setServiceItem(null));
        relatedBlogs.forEach(item -> item.setServiceItem(null));
        pricingCards.forEach(item -> item.setServiceItem(null));
    }

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

    public ImgUrl getIcon() {
        return icon;
    }

    public ServiceItem icon(ImgUrl imgUrl) {
        this.icon = imgUrl;
        return this;
    }

    public void setIcon(ImgUrl imgUrl) {
        this.icon = imgUrl;
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
        imgUrl.getServiceItems().add(this);
        return this;
    }

    public ServiceItem removeCustomerImgUrls(ImgUrl imgUrl) {
        this.customerImgUrls.remove(imgUrl);
        imgUrl.getServiceItems().remove(this);
        return this;
    }

    public void setCustomerImgUrls(Set<ImgUrl> imgUrls) {
        this.customerImgUrls = imgUrls;
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
        return this;
    }

    public ServiceItem removeRelatedVideos(Video video) {
        this.relatedVideos.remove(video);
        return this;
    }

    public void setRelatedVideos(Set<Video> videos) {
        this.relatedVideos = videos;
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
