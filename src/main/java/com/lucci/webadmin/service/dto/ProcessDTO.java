package com.lucci.webadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.Process} entity.
 */
public class ProcessDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private Long order;


    private Long serviceItemId;

    private String serviceItemName;

    private Long iconId;

    private String iconName;
    
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

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessDTO)) {
            return false;
        }

        return id != null && id.equals(((ProcessDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", order=" + getOrder() +
            ", serviceItemId=" + getServiceItemId() +
            ", serviceItemName='" + getServiceItemName() + "'" +
            ", iconId=" + getIconId() +
            ", iconName='" + getIconName() + "'" +
            "}";
    }
}
