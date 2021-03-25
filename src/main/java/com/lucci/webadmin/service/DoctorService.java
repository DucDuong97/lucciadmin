package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.Doctor;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Doctor}.
 */
public interface DoctorService {

    /**
     * Save a doctor.
     *
     * @param doctor the entity to save.
     * @return the persisted entity.
     */
    Doctor save(Doctor doctor);

    /**
     * Get all the doctors.
     *
     * @return the list of entities.
     */
    List<Doctor> findAll();
    /**
     * Get all the DoctorDTO where Bookings is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Doctor> findAllWhereBookingsIsNull();


    /**
     * Get the "id" doctor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Doctor> findOne(Long id);

    /**
     * Delete the "id" doctor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
