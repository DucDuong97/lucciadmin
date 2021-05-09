package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.ServiceOption;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ServiceOption}.
 */
public interface ServiceOptionService {

    /**
     * Save a serviceOption.
     *
     * @param serviceOption the entity to save.
     * @return the persisted entity.
     */
    ServiceOption save(ServiceOption serviceOption);

    /**
     * Get all the serviceOptions.
     *
     * @return the list of entities.
     */
    List<ServiceOption> findAll();


    /**
     * Get the "id" serviceOption.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOption> findOne(Long id);

    /**
     * Delete the "id" serviceOption.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
