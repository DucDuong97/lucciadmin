package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.AchievementService;
import com.lucci.webadmin.domain.Achievement;
import com.lucci.webadmin.repository.AchievementRepository;
import com.lucci.webadmin.service.dto.AchievementDTO;
import com.lucci.webadmin.service.mapper.AchievementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Achievement}.
 */
@Service
@Transactional
public class AchievementServiceImpl implements AchievementService {

    private final Logger log = LoggerFactory.getLogger(AchievementServiceImpl.class);

    private final AchievementRepository achievementRepository;

    private final AchievementMapper achievementMapper;

    public AchievementServiceImpl(AchievementRepository achievementRepository, AchievementMapper achievementMapper) {
        this.achievementRepository = achievementRepository;
        this.achievementMapper = achievementMapper;
    }

    @Override
    public AchievementDTO save(AchievementDTO achievementDTO) {
        log.debug("Request to save Achievement : {}", achievementDTO);
        Achievement achievement = achievementMapper.toEntity(achievementDTO);
        achievement = achievementRepository.save(achievement);
        return achievementMapper.toDto(achievement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AchievementDTO> findAll() {
        log.debug("Request to get all Achievements");
        return achievementRepository.findAll().stream()
            .map(achievementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AchievementDTO> findOne(Long id) {
        log.debug("Request to get Achievement : {}", id);
        return achievementRepository.findById(id)
            .map(achievementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Achievement : {}", id);
        achievementRepository.deleteById(id);
    }
}
