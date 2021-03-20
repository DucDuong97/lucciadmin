package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.Policy;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Policy}.
 */
public interface PolicyService {

    /**
     * Save a policy.
     *
     * @param policy the entity to save.
     * @return the persisted entity.
     */
    Policy save(Policy policy);

    /**
     * Get all the policies.
     *
     * @return the list of entities.
     */
    List<Policy> findAll();


    /**
     * Get the "id" policy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Policy> findOne(Long id);

    /**
     * Delete the "id" policy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
