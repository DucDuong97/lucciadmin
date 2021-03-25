package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Treatment.
 */
@Entity
@Table(name = "treatment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Treatment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private MedicalRecord record;

    @OneToMany(mappedBy = "treatment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TreatmentHistory> histories = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "treatment_doctor",
               joinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"))
    private Set<Doctor> doctors = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "treatment_nurse",
               joinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "nurse_id", referencedColumnName = "id"))
    private Set<Nurse> nurses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "treatments", allowSetters = true)
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedicalRecord getRecord() {
        return record;
    }

    public Treatment record(MedicalRecord medicalRecord) {
        this.record = medicalRecord;
        return this;
    }

    public void setRecord(MedicalRecord medicalRecord) {
        this.record = medicalRecord;
    }

    public Set<TreatmentHistory> getHistories() {
        return histories;
    }

    public Treatment histories(Set<TreatmentHistory> treatmentHistories) {
        this.histories = treatmentHistories;
        return this;
    }

    public Treatment addHistories(TreatmentHistory treatmentHistory) {
        this.histories.add(treatmentHistory);
        treatmentHistory.setTreatment(this);
        return this;
    }

    public Treatment removeHistories(TreatmentHistory treatmentHistory) {
        this.histories.remove(treatmentHistory);
        treatmentHistory.setTreatment(null);
        return this;
    }

    public void setHistories(Set<TreatmentHistory> treatmentHistories) {
        this.histories = treatmentHistories;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public Treatment doctors(Set<Doctor> doctors) {
        this.doctors = doctors;
        return this;
    }

    public Treatment addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.getTreatments().add(this);
        return this;
    }

    public Treatment removeDoctor(Doctor doctor) {
        this.doctors.remove(doctor);
        doctor.getTreatments().remove(this);
        return this;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Set<Nurse> getNurses() {
        return nurses;
    }

    public Treatment nurses(Set<Nurse> nurses) {
        this.nurses = nurses;
        return this;
    }

    public Treatment addNurse(Nurse nurse) {
        this.nurses.add(nurse);
        nurse.getTreatments().add(this);
        return this;
    }

    public Treatment removeNurse(Nurse nurse) {
        this.nurses.remove(nurse);
        nurse.getTreatments().remove(this);
        return this;
    }

    public void setNurses(Set<Nurse> nurses) {
        this.nurses = nurses;
    }

    public Patient getPatient() {
        return patient;
    }

    public Treatment patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
            "}";
    }
}
