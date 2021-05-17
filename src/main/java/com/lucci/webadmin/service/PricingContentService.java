package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.PricingContent;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PricingContent}.
 */
public interface PricingContentService {

    /**
     * Save a pricingContent.
     *
     * @param pricingContent the entity to save.
     * @return the persisted entity.
     */
    PricingContent save(PricingContent pricingContent);

    /**
     * Get all the pricingContents.
     *
     * @return the list of entities.
     */
    List<PricingContent> findAll();


    /**
     * Get the "id" pricingContent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PricingContent> findOne(Long id);

    /**
     * Delete the "id" pricingContent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
