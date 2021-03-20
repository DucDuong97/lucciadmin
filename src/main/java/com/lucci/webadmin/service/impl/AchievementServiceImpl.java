package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.AchievementService;
import com.lucci.webadmin.domain.Achievement;
import com.lucci.webadmin.repository.AchievementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Achievement}.
 */
@Service
@Transactional
public class AchievementServiceImpl implements AchievementService {

    private final Logger log = LoggerFactory.getLogger(AchievementServiceImpl.class);

    private final AchievementRepository achievementRepository;

    public AchievementServiceImpl(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public Achievement save(Achievement achievement) {
        log.debug("Request to save Achievement : {}", achievement);
        return achievementRepository.save(achievement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Achievement> findAll() {
        log.debug("Request to get all Achievements");
        return achievementRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Achievement> findOne(Long id) {
        log.debug("Request to get Achievement : {}", id);
        return achievementRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Achievement : {}", id);
        achievementRepository.deleteById(id);
    }
}
