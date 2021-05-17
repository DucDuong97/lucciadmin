package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.Branch;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Branch}.
 */
public interface BranchService {

    /**
     * Save a branch.
     *
     * @param branch the entity to save.
     * @return the persisted entity.
     */
    Branch save(Branch branch);

    /**
     * Get all the branches.
     *
     * @return the list of entities.
     */
    List<Branch> findAll();


    /**
     * Get the "id" branch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Branch> findOne(Long id);

    /**
     * Delete the "id" branch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
