package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.TreatmentHistory;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TreatmentHistory}.
 */
public interface TreatmentHistoryService {

    /**
     * Save a treatmentHistory.
     *
     * @param treatmentHistory the entity to save.
     * @return the persisted entity.
     */
    TreatmentHistory save(TreatmentHistory treatmentHistory);

    /**
     * Get all the treatmentHistories.
     *
     * @return the list of entities.
     */
    List<TreatmentHistory> findAll();


    /**
     * Get the "id" treatmentHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TreatmentHistory> findOne(Long id);

    /**
     * Delete the "id" treatmentHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
