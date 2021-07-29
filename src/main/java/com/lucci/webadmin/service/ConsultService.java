package com.lucci.webadmin.service;

import com.lucci.webadmin.service.dto.ConsultDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.lucci.webadmin.domain.Consult}.
 */
public interface ConsultService {

    /**
     * Save a consult.
     *
     * @param consultDTO the entity to save.
     * @return the persisted entity.
     */
    ConsultDTO save(ConsultDTO consultDTO);

    /**
     * Get all the consults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsultDTO> findAll(Pageable pageable);

    /**
     * Get all the consults with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ConsultDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" consult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsultDTO> findOne(Long id);

    /**
     * Delete the "id" consult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
