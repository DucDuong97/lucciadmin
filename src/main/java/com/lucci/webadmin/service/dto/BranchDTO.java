package com.lucci.webadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.Branch} entity.
 */
public class BranchDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String adress;

    @NotNull
    private String city;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BranchDTO)) {
            return false;
        }

        return id != null && id.equals(((BranchDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BranchDTO{" +
            "id=" + getId() +
            ", adress='" + getAdress() + "'" +
            ", city='" + getCity() + "'" +
            "}";
    }
}
