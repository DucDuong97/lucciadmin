package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.Treatment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Treatment}.
 */
public interface TreatmentService {

    /**
     * Save a treatment.
     *
     * @param treatment the entity to save.
     * @return the persisted entity.
     */
    Treatment save(Treatment treatment);

    /**
     * Get all the treatments.
     *
     * @return the list of entities.
     */
    List<Treatment> findAll();

    /**
     * Get all the treatments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Treatment> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" treatment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Treatment> findOne(Long id);

    /**
     * Delete the "id" treatment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
