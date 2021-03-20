package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Page}.
 */
public interface PageService {

    /**
     * Save a page.
     *
     * @param page the entity to save.
     * @return the persisted entity.
     */
    Page save(Page page);

    /**
     * Get all the pages.
     *
     * @return the list of entities.
     */
    List<Page> findAll();


    /**
     * Get the "id" page.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Page> findOne(Long id);

    /**
     * Delete the "id" page.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
