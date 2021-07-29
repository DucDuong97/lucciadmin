package com.lucci.webadmin.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.Consult} entity.
 */
public class ConsultDTO implements Serializable {
    
    private Long id;

    @NotNull
    private ZonedDateTime time;

    private String note;


    private Long customerId;

    private String customerName;

    private Long branchId;

    private String branchAdress;

    private Long consultingDoctorId;

    private String consultingDoctorName;
    private Set<PricingCardDTO> services = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public Long getConsultingDoctorId() {
        return consultingDoctorId;
    }

    public void setConsultingDoctorId(Long employeeId) {
        this.consultingDoctorId = employeeId;
    }

    public String getConsultingDoctorName() {
        return consultingDoctorName;
    }

    public void setConsultingDoctorName(String employeeName) {
        this.consultingDoctorName = employeeName;
    }

    public Set<PricingCardDTO> getServices() {
        return services;
    }

    public void setServices(Set<PricingCardDTO> pricingCards) {
        this.services = pricingCards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsultDTO)) {
            return false;
        }

        return id != null && id.equals(((ConsultDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsultDTO{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", note='" + getNote() + "'" +
            ", customerId=" + getCustomerId() +
            ", customerName='" + getCustomerName() + "'" +
            ", branchId=" + getBranchId() +
            ", branchAdress='" + getBranchAdress() + "'" +
            ", consultingDoctorId=" + getConsultingDoctorId() +
            ", consultingDoctorName='" + getConsultingDoctorName() + "'" +
            ", services='" + getServices() + "'" +
            "}";
    }
}
