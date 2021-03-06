package com.lucci.webadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.PricingCard} entity.
 */
public class PricingCardDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    private List<PricingContentDTO> pricingContents;

    private Long serviceItemId;

    private String serviceItemName;

    private Long imgUrlId;

    private String imgUrlName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getServiceItemId() {
        return serviceItemId;
    }

    public void setServiceItemId(Long serviceItemId) {
        this.serviceItemId = serviceItemId;
    }

    public String getServiceItemName() {
        return serviceItemName;
    }

    public void setServiceItemName(String serviceItemName) {
        this.serviceItemName = serviceItemName;
    }

    public List<PricingContentDTO> getPricingContents() {
        return pricingContents;
    }

    public void setPricingContents(List<PricingContentDTO> pricingContents) {
        this.pricingContents = pricingContents;
    }

    public Long getImgUrlId() {
        return imgUrlId;
    }

    public void setImgUrlId(Long imgUrlId) {
        this.imgUrlId = imgUrlId;
    }

    public String getImgUrlName() {
        return imgUrlName;
    }

    public void setImgUrlName(String imgUrlName) {
        this.imgUrlName = imgUrlName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PricingCardDTO)) {
            return false;
        }

        return id != null && id.equals(((PricingCardDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PricingCardDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", serviceItemId=" + getServiceItemId() +
            ", serviceItemName='" + getServiceItemName() + "'" +
            ", imgUrlId=" + getImgUrlId() +
            ", imgUrlName='" + getImgUrlName() + "'" +
            "}";
    }
}
