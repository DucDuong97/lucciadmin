package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.CustomerReview;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CustomerReview}.
 */
public interface CustomerReviewService {

    /**
     * Save a customerReview.
     *
     * @param customerReview the entity to save.
     * @return the persisted entity.
     */
    CustomerReview save(CustomerReview customerReview);

    /**
     * Get all the customerReviews.
     *
     * @return the list of entities.
     */
    List<CustomerReview> findAll();


    /**
     * Get the "id" customerReview.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerReview> findOne(Long id);

    /**
     * Delete the "id" customerReview.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
