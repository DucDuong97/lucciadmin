package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.Person;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Person}.
 */
public interface PersonService {

    /**
     * Save a person.
     *
     * @param person the entity to save.
     * @return the persisted entity.
     */
    Person save(Person person);

    /**
     * Get all the people.
     *
     * @return the list of entities.
     */
    List<Person> findAll();
    /**
     * Get all the PersonDTO where Doctor is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Person> findAllWhereDoctorIsNull();
    /**
     * Get all the PersonDTO where Nurse is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Person> findAllWhereNurseIsNull();
    /**
     * Get all the PersonDTO where Patient is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Person> findAllWherePatientIsNull();
    /**
     * Get all the PersonDTO where Receptionist is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Person> findAllWhereReceptionistIsNull();
    /**
     * Get all the PersonDTO where Accountant is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Person> findAllWhereAccountantIsNull();


    /**
     * Get the "id" person.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Person> findOne(Long id);

    /**
     * Delete the "id" person.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
