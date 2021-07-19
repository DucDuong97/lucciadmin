package com.lucci.webadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.ImgUrl} entity.
 */
public class ImgUrlDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String imgUrl;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImgUrlDTO)) {
            return false;
        }

        return id != null && id.equals(((ImgUrlDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImgUrlDTO{" +
            "id=" + getId() +
            ", imgUrl='" + getImgUrl() + "'" +
            "}";
    }
}
