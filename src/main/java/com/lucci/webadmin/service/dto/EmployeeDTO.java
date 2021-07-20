package com.lucci.webadmin.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.lucci.webadmin.domain.enumeration.Gender;
import com.lucci.webadmin.domain.enumeration.EmployeeRole;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    private Integer phone;

    private String address;

    @NotNull
    private LocalDate birth;

    @NotNull
    private Gender gender;

    @NotNull
    private EmployeeRole role;


    private Long workAtId;
    
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public Long getWorkAtId() {
        return workAtId;
    }

    public void setWorkAtId(Long branchId) {
        this.workAtId = branchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone=" + getPhone() +
            ", address='" + getAddress() + "'" +
            ", birth='" + getBirth() + "'" +
            ", gender='" + getGender() + "'" +
            ", role='" + getRole() + "'" +
            ", workAtId=" + getWorkAtId() +
            "}";
    }
}
