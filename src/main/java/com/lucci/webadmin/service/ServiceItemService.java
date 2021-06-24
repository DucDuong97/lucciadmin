package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.ServiceItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ServiceItem}.
 */
public interface ServiceItemService {

    /**
     * Save a serviceItem.
     *
     * @param serviceItem the entity to save.
     * @return the persisted entity.
     */
    ServiceItem save(ServiceItem serviceItem);

    /**
     * Get all the serviceItems.
     *
     * @return the list of entities.
     */
    List<ServiceItem> findAll();

    /**
     * Get all the serviceItems with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ServiceItem> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" serviceItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceItem> findOne(Long id);

    /**
     * Delete the "id" serviceItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
