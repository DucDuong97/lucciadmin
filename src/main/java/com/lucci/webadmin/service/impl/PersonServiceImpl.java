package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.PersonService;
import com.lucci.webadmin.domain.Person;
import com.lucci.webadmin.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Person}.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person save(Person person) {
        log.debug("Request to save Person : {}", person);
        return personRepository.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        log.debug("Request to get all People");
        return personRepository.findAll();
    }



    /**
     *  Get all the people where Doctor is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Person> findAllWhereDoctorIsNull() {
        log.debug("Request to get all people where Doctor is null");
        return StreamSupport
            .stream(personRepository.findAll().spliterator(), false)
            .filter(person -> person.getDoctor() == null)
            .collect(Collectors.toList());
    }


    /**
     *  Get all the people where Nurse is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Person> findAllWhereNurseIsNull() {
        log.debug("Request to get all people where Nurse is null");
        return StreamSupport
            .stream(personRepository.findAll().spliterator(), false)
            .filter(person -> person.getNurse() == null)
            .collect(Collectors.toList());
    }


    /**
     *  Get all the people where Patient is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Person> findAllWherePatientIsNull() {
        log.debug("Request to get all people where Patient is null");
        return StreamSupport
            .stream(personRepository.findAll().spliterator(), false)
            .filter(person -> person.getPatient() == null)
            .collect(Collectors.toList());
    }


    /**
     *  Get all the people where Receptionist is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Person> findAllWhereReceptionistIsNull() {
        log.debug("Request to get all people where Receptionist is null");
        return StreamSupport
            .stream(personRepository.findAll().spliterator(), false)
            .filter(person -> person.getReceptionist() == null)
            .collect(Collectors.toList());
    }


    /**
     *  Get all the people where Accountant is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Person> findAllWhereAccountantIsNull() {
        log.debug("Request to get all people where Accountant is null");
        return StreamSupport
            .stream(personRepository.findAll().spliterator(), false)
            .filter(person -> person.getAccountant() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> findOne(Long id) {
        log.debug("Request to get Person : {}", id);
        return personRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Person : {}", id);
        personRepository.deleteById(id);
    }
}
