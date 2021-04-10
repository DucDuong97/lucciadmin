package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lucci.webadmin.utils.NoUTCInstant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.lucci.webadmin.domain.enumeration.Gender;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "adress")
    private String adress;

    @NotNull
    @JsonDeserialize(converter = NoUTCInstant.class)
    @Column(name = "birth", nullable = false)
    private Instant birth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Doctor doctor;

    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Nurse nurse;

    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Patient patient;

    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Receptionist receptionist;

    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Accountant accountant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public Person phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public Person adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Instant getBirth() {
        return birth;
    }

    public Person birth(Instant birth) {
        this.birth = birth;
        return this;
    }

    public void setBirth(Instant birth) {
        this.birth = birth;
    }

    public Gender getGender() {
        return gender;
    }

    public Person gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Person doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public Person nurse(Nurse nurse) {
        this.nurse = nurse;
        return this;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Patient getPatient() {
        return patient;
    }

    public Person patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Receptionist getReceptionist() {
        return receptionist;
    }

    public Person receptionist(Receptionist receptionist) {
        this.receptionist = receptionist;
        return this;
    }

    public void setReceptionist(Receptionist receptionist) {
        this.receptionist = receptionist;
    }

    public Accountant getAccountant() {
        return accountant;
    }

    public Person accountant(Accountant accountant) {
        this.accountant = accountant;
        return this;
    }

    public void setAccountant(Accountant accountant) {
        this.accountant = accountant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", adress='" + getAdress() + "'" +
            ", birth='" + getBirth() + "'" +
            ", gender='" + getGender() + "'" +
            "}";
    }
}
