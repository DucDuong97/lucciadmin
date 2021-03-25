package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.MedicalRecord;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link MedicalRecord}.
 */
public interface MedicalRecordService {

    /**
     * Save a medicalRecord.
     *
     * @param medicalRecord the entity to save.
     * @return the persisted entity.
     */
    MedicalRecord save(MedicalRecord medicalRecord);

    /**
     * Get all the medicalRecords.
     *
     * @return the list of entities.
     */
    List<MedicalRecord> findAll();


    /**
     * Get the "id" medicalRecord.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalRecord> findOne(Long id);

    /**
     * Delete the "id" medicalRecord.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
