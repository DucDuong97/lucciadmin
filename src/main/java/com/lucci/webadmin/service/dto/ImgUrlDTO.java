package com.lucci.webadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.ImgUrl} entity.
 */
public class ImgUrlDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String path;

    private String imgUrl;

    private Set<TreatmentDTO> treatments = new HashSet<>();

    private Set<ServiceItemDTO> serviceItems = new HashSet<>();


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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<TreatmentDTO> getTreatments() {
        return treatments;
    }

    public void setTreatments(Set<TreatmentDTO> treatments) {
        this.treatments = treatments;
    }

    public Set<ServiceItemDTO> getServiceItems() {
        return serviceItems;
    }

    public void setServiceItems(Set<ServiceItemDTO> serviceItems) {
        this.serviceItems = serviceItems;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String createAccessKey() {
        return path + "/" + name;
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
            ", name='" + getName() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }
}
