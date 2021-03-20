package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.Achievement;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Achievement}.
 */
public interface AchievementService {

    /**
     * Save a achievement.
     *
     * @param achievement the entity to save.
     * @return the persisted entity.
     */
    Achievement save(Achievement achievement);

    /**
     * Get all the achievements.
     *
     * @return the list of entities.
     */
    List<Achievement> findAll();


    /**
     * Get the "id" achievement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Achievement> findOne(Long id);

    /**
     * Delete the "id" achievement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
