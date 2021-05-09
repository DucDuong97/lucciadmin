package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.ImgUrl;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ImgUrl}.
 */
public interface ImgUrlService {

    /**
     * Save a imgUrl.
     *
     * @param imgUrl the entity to save.
     * @return the persisted entity.
     */
    ImgUrl save(ImgUrl imgUrl);

    /**
     * Get all the imgUrls.
     *
     * @return the list of entities.
     */
    List<ImgUrl> findAll();


    /**
     * Get the "id" imgUrl.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImgUrl> findOne(Long id);

    /**
     * Delete the "id" imgUrl.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
