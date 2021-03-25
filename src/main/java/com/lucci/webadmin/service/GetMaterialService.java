package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.GetMaterial;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link GetMaterial}.
 */
public interface GetMaterialService {

    /**
     * Save a getMaterial.
     *
     * @param getMaterial the entity to save.
     * @return the persisted entity.
     */
    GetMaterial save(GetMaterial getMaterial);

    /**
     * Get all the getMaterials.
     *
     * @return the list of entities.
     */
    List<GetMaterial> findAll();


    /**
     * Get the "id" getMaterial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GetMaterial> findOne(Long id);

    /**
     * Delete the "id" getMaterial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
