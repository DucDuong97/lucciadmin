package com.lucci.webadmin.service;

import com.lucci.webadmin.service.dto.PricingCardDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lucci.webadmin.domain.PricingCard}.
 */
public interface PricingCardService {

    /**
     * Save a pricingCard.
     *
     * @param pricingCardDTO the entity to save.
     * @return the persisted entity.
     */
    PricingCardDTO save(PricingCardDTO pricingCardDTO);

    /**
     * Get all the pricingCards.
     *
     * @return the list of entities.
     */
    List<PricingCardDTO> findAll();


    /**
     * Get the "id" pricingCard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PricingCardDTO> findOne(Long id);

    /**
     * Delete the "id" pricingCard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
