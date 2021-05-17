package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.PricingCard;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PricingCard}.
 */
public interface PricingCardService {

    /**
     * Save a pricingCard.
     *
     * @param pricingCard the entity to save.
     * @return the persisted entity.
     */
    PricingCard save(PricingCard pricingCard);

    /**
     * Get all the pricingCards.
     *
     * @return the list of entities.
     */
    List<PricingCard> findAll();


    /**
     * Get the "id" pricingCard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PricingCard> findOne(Long id);

    /**
     * Delete the "id" pricingCard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
