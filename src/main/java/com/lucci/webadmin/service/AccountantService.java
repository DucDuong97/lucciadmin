package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.Accountant;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Accountant}.
 */
public interface AccountantService {

    /**
     * Save a accountant.
     *
     * @param accountant the entity to save.
     * @return the persisted entity.
     */
    Accountant save(Accountant accountant);

    /**
     * Get all the accountants.
     *
     * @return the list of entities.
     */
    List<Accountant> findAll();


    /**
     * Get the "id" accountant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Accountant> findOne(Long id);

    /**
     * Delete the "id" accountant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
