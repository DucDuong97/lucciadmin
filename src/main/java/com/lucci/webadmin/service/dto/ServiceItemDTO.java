package com.lucci.webadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.ServiceItem} entity.
 */
public class ServiceItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;


    private Long iconId;

    private String iconName;
    private Set<ImgUrlDTO> customerImgUrls = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIconId() {
        return iconId;
    }

    public void setIconId(Long imgUrlId) {
        this.iconId = imgUrlId;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String imgUrlName) {
        this.iconName = imgUrlName;
    }

    public Set<ImgUrlDTO> getCustomerImgUrls() {
        return customerImgUrls;
    }

    public void setCustomerImgUrls(Set<ImgUrlDTO> imgUrls) {
        this.customerImgUrls = imgUrls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceItemDTO)) {
            return false;
        }

        return id != null && id.equals(((ServiceItemDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceItemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", iconId=" + getIconId() +
            ", iconName='" + getIconName() + "'" +
            ", customerImgUrls='" + getCustomerImgUrls() + "'" +
            "}";
    }
}
