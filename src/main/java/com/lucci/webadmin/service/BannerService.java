package com.lucci.webadmin.service;

import com.lucci.webadmin.service.dto.BannerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lucci.webadmin.domain.Banner}.
 */
public interface BannerService {

    /**
     * Save a banner.
     *
     * @param bannerDTO the entity to save.
     * @return the persisted entity.
     */
    BannerDTO save(BannerDTO bannerDTO);

    /**
     * Get all the banners.
     *
     * @return the list of entities.
     */
    List<BannerDTO> findAll();


    /**
     * Get the "id" banner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BannerDTO> findOne(Long id);

    /**
     * Delete the "id" banner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
