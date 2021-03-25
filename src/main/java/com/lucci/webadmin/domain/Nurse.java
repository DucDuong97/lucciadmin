package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Nurse.
 */
@Entity
@Table(name = "nurse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Nurse implements Serializable {

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

    @ManyToMany(mappedBy = "nurses")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Treatment> treatments = new HashSet<>();

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

    public Nurse salary(Integer salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Person getPerson() {
        return person;
    }

    public Nurse person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public Nurse treatments(Set<Treatment> treatments) {
        this.treatments = treatments;
        return this;
    }

    public Nurse addTreatments(Treatment treatment) {
        this.treatments.add(treatment);
        treatment.getNurses().add(this);
        return this;
    }

    public Nurse removeTreatments(Treatment treatment) {
        this.treatments.remove(treatment);
        treatment.getNurses().remove(this);
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
        if (!(o instanceof Nurse)) {
            return false;
        }
        return id != null && id.equals(((Nurse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nurse{" +
            "id=" + getId() +
            ", salary=" + getSalary() +
            "}";
    }
}
