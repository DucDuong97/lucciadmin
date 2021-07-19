package com.lucci.webadmin.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.lucci.webadmin.domain.Treatment} entity.
 */
public class TreatmentDTO implements Serializable {
    
    private Long id;

    private String description;

    @NotNull
    private LocalDate date;

    private String nextPlan;

    private LocalDate revisitDate;


    private Long doctorId;

    private String doctorName;
    private Set<ImgUrlDTO> treatmentImgUrls = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNextPlan() {
        return nextPlan;
    }

    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
    }

    public LocalDate getRevisitDate() {
        return revisitDate;
    }

    public void setRevisitDate(LocalDate revisitDate) {
        this.revisitDate = revisitDate;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long employeeId) {
        this.doctorId = employeeId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String employeeName) {
        this.doctorName = employeeName;
    }

    public Set<ImgUrlDTO> getTreatmentImgUrls() {
        return treatmentImgUrls;
    }

    public void setTreatmentImgUrls(Set<ImgUrlDTO> imgUrls) {
        this.treatmentImgUrls = imgUrls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TreatmentDTO)) {
            return false;
        }

        return id != null && id.equals(((TreatmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TreatmentDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", nextPlan='" + getNextPlan() + "'" +
            ", revisitDate='" + getRevisitDate() + "'" +
            ", doctorId=" + getDoctorId() +
            ", doctorName='" + getDoctorName() + "'" +
            ", treatmentImgUrls='" + getTreatmentImgUrls() + "'" +
            "}";
    }
}
