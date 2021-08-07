package com.lucci.webadmin.service;

import com.lucci.webadmin.service.dto.PricingContentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lucci.webadmin.domain.PricingContent}.
 */
public interface PricingContentService {

    /**
     * Save a pricingContent.
     *
     * @param pricingContentDTO the entity to save.
     * @return the persisted entity.
     */
    PricingContentDTO save(PricingContentDTO pricingContentDTO);

    /**
     * Get all the pricingContents.
     *
     * @return the list of entities.
     */
    List<PricingContentDTO> findAll();


    /**
     * Get the "id" pricingContent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PricingContentDTO> findOne(Long id);

    /**
     * Delete the "id" pricingContent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
