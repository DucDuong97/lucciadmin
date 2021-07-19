package com.lucci.webadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.TreatmentPlan} entity.
 */
public class TreatmentPlanDTO implements Serializable {
    
    private Long id;

    private String presentComplaint;

    private String pastMedicalHistory;

    private String note;


    private Long customerId;

    private String customerName;

    private Long serviceId;

    private String serviceName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresentComplaint() {
        return presentComplaint;
    }

    public void setPresentComplaint(String presentComplaint) {
        this.presentComplaint = presentComplaint;
    }

    public String getPastMedicalHistory() {
        return pastMedicalHistory;
    }

    public void setPastMedicalHistory(String pastMedicalHistory) {
        this.pastMedicalHistory = pastMedicalHistory;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TreatmentPlanDTO)) {
            return false;
        }

        return id != null && id.equals(((TreatmentPlanDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TreatmentPlanDTO{" +
            "id=" + getId() +
            ", presentComplaint='" + getPresentComplaint() + "'" +
            ", pastMedicalHistory='" + getPastMedicalHistory() + "'" +
            ", note='" + getNote() + "'" +
            ", customerId=" + getCustomerId() +
            ", customerName='" + getCustomerName() + "'" +
            ", serviceId=" + getServiceId() +
            ", serviceName='" + getServiceName() + "'" +
            "}";
    }
}
