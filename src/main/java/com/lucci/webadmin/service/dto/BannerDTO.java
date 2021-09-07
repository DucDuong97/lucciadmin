package com.lucci.webadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.Banner} entity.
 */
public class BannerDTO implements Serializable {
    
    private Long id;


    private Long imgUrlId;

    private String imgUrlName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof BannerDTO)) {
            return false;
        }

        return id != null && id.equals(((BannerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BannerDTO{" +
            "id=" + getId() +
            ", imgUrlId=" + getImgUrlId() +
            ", imgUrlName='" + getImgUrlName() + "'" +
            "}";
    }
}
