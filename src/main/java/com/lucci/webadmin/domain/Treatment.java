package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lucci.webadmin.domain.enumeration.TreatmentState;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Treatment.
 */
@Entity
@Table(name = "treatment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Treatment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "next_plan")
    private String nextPlan;

    @Column(name = "revisit_date")
    private LocalDate revisitDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "treatments", allowSetters = true)
    private Employee doctor;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "treatment_treatment_img_url",
               joinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "treatment_img_url_id", referencedColumnName = "id"))
    private Set<ImgUrl> treatmentImgUrls = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "treatments", allowSetters = true)
    private TreatmentPlan treatmentPlan;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private TreatmentState state;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Treatment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public Treatment date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNextPlan() {
        return nextPlan;
    }

    public Treatment nextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
        return this;
    }

    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
    }

    public LocalDate getRevisitDate() {
        return revisitDate;
    }

    public Treatment revisitDate(LocalDate revisitDate) {
        this.revisitDate = revisitDate;
        return this;
    }

    public void setRevisitDate(LocalDate revisitDate) {
        this.revisitDate = revisitDate;
    }

    public Employee getDoctor() {
        return doctor;
    }

    public Treatment doctor(Employee employee) {
        this.doctor = employee;
        return this;
    }

    public void setDoctor(Employee employee) {
        this.doctor = employee;
    }

    public Set<ImgUrl> getTreatmentImgUrls() {
        return treatmentImgUrls;
    }

    public Treatment treatmentImgUrls(Set<ImgUrl> imgUrls) {
        this.treatmentImgUrls = imgUrls;
        return this;
    }

    public Treatment addTreatmentImgUrl(ImgUrl imgUrl) {
        this.treatmentImgUrls.add(imgUrl);
        imgUrl.getTreatments().add(this);
        return this;
    }

    public Treatment removeTreatmentImgUrl(ImgUrl imgUrl) {
        this.treatmentImgUrls.remove(imgUrl);
        imgUrl.getTreatments().remove(this);
        return this;
    }

    public void setTreatmentImgUrls(Set<ImgUrl> imgUrls) {
        this.treatmentImgUrls = imgUrls;
    }

    public TreatmentPlan getTreatmentPlan() {
        return treatmentPlan;
    }

    public Treatment treatmentPlan(TreatmentPlan treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
        return this;
    }

    public void setTreatmentPlan(TreatmentPlan treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public TreatmentState getState() {
        return state;
    }

    public void setState(TreatmentState state) {
        this.state = state;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Treatment)) {
            return false;
        }
        return id != null && id.equals(((Treatment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Treatment{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", nextPlan='" + getNextPlan() + "'" +
            ", revisitDate='" + getRevisitDate() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
