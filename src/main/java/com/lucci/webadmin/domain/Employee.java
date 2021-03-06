package com.lucci.webadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import com.lucci.webadmin.domain.enumeration.Gender;

import com.lucci.webadmin.domain.enumeration.EmployeeRole;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @NotNull
    @Column(name = "phone", nullable = false, unique = true)
    private Integer phone;

    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private EmployeeRole role;

    @ManyToOne
    @JsonIgnoreProperties(value = "employees", allowSetters = true)
    private Branch workAt;

    @JsonIgnore
    @OneToOne(mappedBy = "relatedEmployee", cascade = CascadeType.PERSIST)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "correspondDoctor", cascade = CascadeType.PERSIST)
    private Set<Booking> bookings;

    @PreRemove
    private void preRemove() {
        if (bookings != null) {
            bookings.forEach(booking -> booking.setCorrespondDoctor(null));
        }
        if (user != null) {
            user.setRelatedEmployee(null);
            user.getAuthorities().clear();
        }
    }

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

    public Employee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public Employee phone(Integer phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public Employee address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public Employee birth(LocalDate birth) {
        this.birth = birth;
        return this;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Gender getGender() {
        return gender;
    }

    public Employee gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public Employee role(EmployeeRole role) {
        this.role = role;
        return this;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public Branch getWorkAt() {
        return workAt;
    }

    public Employee workAt(Branch branch) {
        this.workAt = branch;
        return this;
    }

    public void setWorkAt(Branch branch) {
        this.workAt = branch;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone=" + getPhone() +
            ", address='" + getAddress() + "'" +
            ", birth='" + getBirth() + "'" +
            ", gender='" + getGender() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
