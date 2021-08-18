package com.lucci.webadmin.service.dto;

import java.time.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.Booking} entity.
 */
public class BookingDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    private Long correspondDoctorId;

    private String correspondDoctorName;

    private Long customerId;

    private String customerName;

    private Long treatmentPlanId;

    private Long branchId;

    private String branchAdress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(getDate(), getTime());
    }

    public Long getCorrespondDoctorId() {
        return correspondDoctorId;
    }

    public void setCorrespondDoctorId(Long employeeId) {
        this.correspondDoctorId = employeeId;
    }

    public String getCorrespondDoctorName() {
        return correspondDoctorName;
    }

    public void setCorrespondDoctorName(String employeeName) {
        this.correspondDoctorName = employeeName;
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

    public Long getTreatmentPlanId() {
        return treatmentPlanId;
    }

    public void setTreatmentPlanId(Long treatmentPlanId) {
        this.treatmentPlanId = treatmentPlanId;
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
        if (!(o instanceof BookingDTO)) {
            return false;
        }

        return id != null && id.equals(((BookingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookingDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", time='" + getTime() + "'" +
            ", correspondDoctorId=" + getCorrespondDoctorId() +
            ", correspondDoctorName='" + getCorrespondDoctorName() + "'" +
            ", customerId=" + getCustomerId() +
            ", customerName='" + getCustomerName() + "'" +
            ", treatmentPlanId=" + getTreatmentPlanId() +
            ", branchId=" + getBranchId() +
            ", branchAdress='" + getBranchAdress() + "'" +
            "}";
    }
}
