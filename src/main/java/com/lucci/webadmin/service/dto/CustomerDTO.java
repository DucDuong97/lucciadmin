package com.lucci.webadmin.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.lucci.webadmin.domain.enumeration.Gender;
import com.lucci.webadmin.domain.enumeration.CustomerTier;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.Customer} entity.
 */
public class CustomerDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 60)
    private String name;

    @NotNull
    @Size(max = 20)
    private String phone;

    private String address;

    @NotNull
    private Instant birth;

    @NotNull
    private Gender gender;

    private CustomerTier tier;

    private Boolean newCustomer;


    private Long correspondConsultantId;
    
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getBirth() {
        return birth;
    }

    public void setBirth(Instant birth) {
        this.birth = birth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public CustomerTier getTier() {
        return tier;
    }

    public void setTier(CustomerTier tier) {
        this.tier = tier;
    }

    public Boolean isNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(Boolean newCustomer) {
        this.newCustomer = newCustomer;
    }

    public Long getCorrespondConsultantId() {
        return correspondConsultantId;
    }

    public void setCorrespondConsultantId(Long userId) {
        this.correspondConsultantId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDTO)) {
            return false;
        }

        return id != null && id.equals(((CustomerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            ", birth='" + getBirth() + "'" +
            ", gender='" + getGender() + "'" +
            ", tier='" + getTier() + "'" +
            ", newCustomer='" + isNewCustomer() + "'" +
            ", correspondConsultantId=" + getCorrespondConsultantId() +
            "}";
    }
}
