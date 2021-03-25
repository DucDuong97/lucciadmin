package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.Nurse;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Nurse}.
 */
public interface NurseService {

    /**
     * Save a nurse.
     *
     * @param nurse the entity to save.
     * @return the persisted entity.
     */
    Nurse save(Nurse nurse);

    /**
     * Get all the nurses.
     *
     * @return the list of entities.
     */
    List<Nurse> findAll();


    /**
     * Get the "id" nurse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Nurse> findOne(Long id);

    /**
     * Delete the "id" nurse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
