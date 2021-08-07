package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.ImgUrl;
import com.lucci.webadmin.service.dto.ImgUrlDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lucci.webadmin.domain.ImgUrl}.
 */
public interface ImgUrlService {

    /**
     * Save a imgUrl.
     *
     * @param imgUrlDTO the entity to save.
     * @return the persisted entity.
     */
    ImgUrlDTO save(ImgUrlDTO imgUrlDTO);

    /**
     * Get all the imgUrls.
     *
     * @return the list of entities.
     */
    List<ImgUrlDTO> findAll();


    /**
     * Get the "id" imgUrl.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImgUrlDTO> findOne(Long id);

    /**
     * Delete the "id" imgUrl.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    ImgUrlDTO upload(MultipartFile file);

    List<ImgUrlDTO> findByTreatmentId(Long treatmentId);

    Optional<ImgUrlDTO> findByURL(String url);
}
