package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lucci.webadmin.domain.enumeration.BookingState;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Booking extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, columnDefinition = " default 'CAME'")
    private BookingState state;

    @ManyToOne
    @JsonIgnoreProperties(value = "bookings", allowSetters = true)
    private Employee correspondDoctor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "bookings", allowSetters = true)
    private Customer customer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "bookings", allowSetters = true)
    private TreatmentPlan treatmentPlan;

    @ManyToOne
    @JsonIgnoreProperties(value = "bookings", allowSetters = true)
    private Branch branch;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Booking time(LocalDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Employee getCorrespondDoctor() {
        return correspondDoctor;
    }

    public Booking correspondDoctor(Employee employee) {
        this.correspondDoctor = employee;
        return this;
    }

    public void setCorrespondDoctor(Employee employee) {
        this.correspondDoctor = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Booking customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public TreatmentPlan getTreatmentPlan() {
        return treatmentPlan;
    }

    public Booking treatmentPlan(TreatmentPlan treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
        return this;
    }

    public void setTreatmentPlan(TreatmentPlan treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public Branch getBranch() {
        return branch;
    }

    public Booking branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        return id != null && id.equals(((Booking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }

    public BookingState getState() {
        return state;
    }

    public void setState(BookingState state) {
        this.state = state;
    }
}
