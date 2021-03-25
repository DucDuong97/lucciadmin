package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.Receptionist;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Receptionist}.
 */
public interface ReceptionistService {

    /**
     * Save a receptionist.
     *
     * @param receptionist the entity to save.
     * @return the persisted entity.
     */
    Receptionist save(Receptionist receptionist);

    /**
     * Get all the receptionists.
     *
     * @return the list of entities.
     */
    List<Receptionist> findAll();


    /**
     * Get the "id" receptionist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Receptionist> findOne(Long id);

    /**
     * Delete the "id" receptionist.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
