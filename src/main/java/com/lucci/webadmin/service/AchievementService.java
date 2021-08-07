package com.lucci.webadmin.service;

import com.lucci.webadmin.service.dto.AchievementDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lucci.webadmin.domain.Achievement}.
 */
public interface AchievementService {

    /**
     * Save a achievement.
     *
     * @param achievementDTO the entity to save.
     * @return the persisted entity.
     */
    AchievementDTO save(AchievementDTO achievementDTO);

    /**
     * Get all the achievements.
     *
     * @return the list of entities.
     */
    List<AchievementDTO> findAll();


    /**
     * Get the "id" achievement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AchievementDTO> findOne(Long id);

    /**
     * Delete the "id" achievement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
