package com.lucci.webadmin.service;

import com.lucci.webadmin.service.dto.PotentialDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.lucci.webadmin.domain.Potential}.
 */
public interface PotentialService {

    /**
     * Save a potential.
     *
     * @param potentialDTO the entity to save.
     * @return the persisted entity.
     */
    PotentialDTO save(PotentialDTO potentialDTO);

    /**
     * Get all the potentials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PotentialDTO> findAll(Pageable pageable);


    /**
     * Get the "id" potential.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PotentialDTO> findOne(Long id);

    /**
     * Delete the "id" potential.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
