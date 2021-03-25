package com.lucci.webadmin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Person person;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Treatment> treatments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public Patient person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public Patient bookings(Set<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }

    public Patient addBookings(Booking booking) {
        this.bookings.add(booking);
        booking.setPatient(this);
        return this;
    }

    public Patient removeBookings(Booking booking) {
        this.bookings.remove(booking);
        booking.setPatient(null);
        return this;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public Patient treatments(Set<Treatment> treatments) {
        this.treatments = treatments;
        return this;
    }

    public Patient addTreatments(Treatment treatment) {
        this.treatments.add(treatment);
        treatment.setPatient(this);
        return this;
    }

    public Patient removeTreatments(Treatment treatment) {
        this.treatments.remove(treatment);
        treatment.setPatient(null);
        return this;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            "}";
    }
}
