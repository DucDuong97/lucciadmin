package com.lucci.webadmin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Receptionist.
 */
@Entity
@Table(name = "receptionist")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Receptionist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "salary")
    private Integer salary;

    @OneToOne
    @JoinColumn(unique = true)
    private Person person;

    @OneToMany(mappedBy = "receptionist")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "receptionist")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<GetMaterial> getMaterials = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSalary() {
        return salary;
    }

    public Receptionist salary(Integer salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Person getPerson() {
        return person;
    }

    public Receptionist person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Receptionist payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public Receptionist addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setReceptionist(this);
        return this;
    }

    public Receptionist removePayment(Payment payment) {
        this.payments.remove(payment);
        payment.setReceptionist(null);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<GetMaterial> getGetMaterials() {
        return getMaterials;
    }

    public Receptionist getMaterials(Set<GetMaterial> getMaterials) {
        this.getMaterials = getMaterials;
        return this;
    }

    public Receptionist addGetMaterials(GetMaterial getMaterial) {
        this.getMaterials.add(getMaterial);
        getMaterial.setReceptionist(this);
        return this;
    }

    public Receptionist removeGetMaterials(GetMaterial getMaterial) {
        this.getMaterials.remove(getMaterial);
        getMaterial.setReceptionist(null);
        return this;
    }

    public void setGetMaterials(Set<GetMaterial> getMaterials) {
        this.getMaterials = getMaterials;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Receptionist)) {
            return false;
        }
        return id != null && id.equals(((Receptionist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Receptionist{" +
            "id=" + getId() +
            ", salary=" + getSalary() +
            "}";
    }
}
