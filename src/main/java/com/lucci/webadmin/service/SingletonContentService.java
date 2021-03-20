package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.SingletonContent;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SingletonContent}.
 */
public interface SingletonContentService {

    /**
     * Save a singletonContent.
     *
     * @param singletonContent the entity to save.
     * @return the persisted entity.
     */
    SingletonContent save(SingletonContent singletonContent);

    /**
     * Get all the singletonContents.
     *
     * @return the list of entities.
     */
    List<SingletonContent> findAll();


    /**
     * Get the "id" singletonContent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SingletonContent> findOne(Long id);

    /**
     * Delete the "id" singletonContent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
