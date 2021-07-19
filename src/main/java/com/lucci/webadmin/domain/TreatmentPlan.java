package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TreatmentPlan.
 */
@Entity
@Table(name = "treatment_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TreatmentPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "present_complaint")
    private String presentComplaint;

    @Column(name = "past_medical_history")
    private String pastMedicalHistory;

    @Column(name = "note")
    private String note;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "treatmentPlans", allowSetters = true)
    private Customer customer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "treatmentPlans", allowSetters = true)
    private PricingCard service;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresentComplaint() {
        return presentComplaint;
    }

    public TreatmentPlan presentComplaint(String presentComplaint) {
        this.presentComplaint = presentComplaint;
        return this;
    }

    public void setPresentComplaint(String presentComplaint) {
        this.presentComplaint = presentComplaint;
    }

    public String getPastMedicalHistory() {
        return pastMedicalHistory;
    }

    public TreatmentPlan pastMedicalHistory(String pastMedicalHistory) {
        this.pastMedicalHistory = pastMedicalHistory;
        return this;
    }

    public void setPastMedicalHistory(String pastMedicalHistory) {
        this.pastMedicalHistory = pastMedicalHistory;
    }

    public String getNote() {
        return note;
    }

    public TreatmentPlan note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Customer getCustomer() {
        return customer;
    }

    public TreatmentPlan customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PricingCard getService() {
        return service;
    }

    public TreatmentPlan service(PricingCard pricingCard) {
        this.service = pricingCard;
        return this;
    }

    public void setService(PricingCard pricingCard) {
        this.service = pricingCard;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TreatmentPlan)) {
            return false;
        }
        return id != null && id.equals(((TreatmentPlan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TreatmentPlan{" +
            "id=" + getId() +
            ", presentComplaint='" + getPresentComplaint() + "'" +
            ", pastMedicalHistory='" + getPastMedicalHistory() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
