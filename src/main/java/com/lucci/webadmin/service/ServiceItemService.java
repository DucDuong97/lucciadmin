package com.lucci.webadmin.service;

import com.lucci.webadmin.service.dto.ServiceItemDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lucci.webadmin.domain.ServiceItem}.
 */
public interface ServiceItemService {

    /**
     * Save a serviceItem.
     *
     * @param serviceItemDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceItemDTO save(ServiceItemDTO serviceItemDTO);

    /**
     * Get all the serviceItems.
     *
     * @return the list of entities.
     */
    List<ServiceItemDTO> findAll();

    /**
     * Get all the serviceItems with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ServiceItemDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" serviceItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceItemDTO> findOne(Long id);

    /**
     * Delete the "id" serviceItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
