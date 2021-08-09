package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lucci.webadmin.domain.enumeration.BookingState;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Consult.
 */
@Entity
@Table(name = "consult")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Consult extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "time", nullable = false)
    private ZonedDateTime time;

    @Column(name = "note")
    private String note;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "consults", allowSetters = true)
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties(value = "consults", allowSetters = true)
    private Branch branch;

    @ManyToOne
    @JsonIgnoreProperties(value = "consults", allowSetters = true)
    private Employee consultingDoctor;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "consult_service",
               joinColumns = @JoinColumn(name = "consult_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    private Set<PricingCard> services = new HashSet<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private BookingState state;


    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Consult time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public Consult note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Consult customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Branch getBranch() {
        return branch;
    }

    public Consult branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Employee getConsultingDoctor() {
        return consultingDoctor;
    }

    public Consult consultingDoctor(Employee employee) {
        this.consultingDoctor = employee;
        return this;
    }

    public void setConsultingDoctor(Employee employee) {
        this.consultingDoctor = employee;
    }

    public Set<PricingCard> getServices() {
        return services;
    }

    public Consult services(Set<PricingCard> pricingCards) {
        this.services = pricingCards;
        return this;
    }

    public Consult addService(PricingCard pricingCard) {
        this.services.add(pricingCard);
        return this;
    }

    public Consult removeService(PricingCard pricingCard) {
        this.services.remove(pricingCard);
        return this;
    }

    public void setServices(Set<PricingCard> pricingCards) {
        this.services = pricingCards;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consult)) {
            return false;
        }
        return id != null && id.equals(((Consult) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Consult{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", note='" + getNote() + "'" +
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
