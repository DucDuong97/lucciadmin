package com.lucci.webadmin.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.lucci.webadmin.domain.enumeration.Gender;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.Potential} entity.
 */
public class PotentialDTO implements Serializable {
    
    private Long id;

    private String name;

    private Integer phone;

    @NotNull
    private Gender gender;

    @NotNull
    private ZonedDateTime time;


    private Long serviceId;

    private String serviceName;

    private Long branchId;

    private String branchAdress;
    
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

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long pricingCardId) {
        this.serviceId = pricingCardId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String pricingCardName) {
        this.serviceName = pricingCardName;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchAdress() {
        return branchAdress;
    }

    public void setBranchAdress(String branchAdress) {
        this.branchAdress = branchAdress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PotentialDTO)) {
            return false;
        }

        return id != null && id.equals(((PotentialDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PotentialDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone=" + getPhone() +
            ", gender='" + getGender() + "'" +
            ", time='" + getTime() + "'" +
            ", serviceId=" + getServiceId() +
            ", serviceName='" + getServiceName() + "'" +
            ", branchId=" + getBranchId() +
            ", branchAdress='" + getBranchAdress() + "'" +
            "}";
    }
}
