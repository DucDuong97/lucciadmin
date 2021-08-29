package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.TreatmentPlan;
import com.lucci.webadmin.repository.TreatmentPlanRepository;
import com.lucci.webadmin.service.dto.TreatmentPlanDTO;
import com.lucci.webadmin.service.mapper.TreatmentPlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TreatmentPlan}.
 */
@Service
@Transactional
public class TreatmentPlanService {

    private final Logger log = LoggerFactory.getLogger(TreatmentPlanService.class);

    private final TreatmentPlanRepository treatmentPlanRepository;

    private final TreatmentPlanMapper treatmentPlanMapper;

    public TreatmentPlanService(TreatmentPlanRepository treatmentPlanRepository, TreatmentPlanMapper treatmentPlanMapper) {
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.treatmentPlanMapper = treatmentPlanMapper;
    }

    /**
     * Save a treatmentPlan.
     *
     * @param treatmentPlanDTO the entity to save.
     * @return the persisted entity.
     */
    public TreatmentPlanDTO save(TreatmentPlanDTO treatmentPlanDTO) {
        log.debug("Request to save TreatmentPlan : {}", treatmentPlanDTO);
        TreatmentPlan treatmentPlan = treatmentPlanMapper.toEntity(treatmentPlanDTO);
        treatmentPlan = treatmentPlanRepository.save(treatmentPlan);
        return treatmentPlanMapper.toDto(treatmentPlan);
    }

    /**
     * Get all the treatmentPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TreatmentPlanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TreatmentPlans");

        return treatmentPlanRepository.findWithAuthority(pageable)
            .map(treatmentPlanMapper::toDto);
    }


    /**
     * Get one treatmentPlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TreatmentPlanDTO> findOne(Long id) {
        log.debug("Request to get TreatmentPlan : {}", id);
        return treatmentPlanRepository.findById(id)
            .map(treatmentPlanMapper::toDto);
    }

    /**
     * Delete the treatmentPlan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TreatmentPlan : {}", id);
        treatmentPlanRepository.deleteById(id);
    }

    public Page<TreatmentPlanDTO> findByCustomerId(Pageable pageable, Long customerId) {
        return treatmentPlanRepository.findByCustomerId(pageable, customerId)
            .map(treatmentPlanMapper::toDto);
    }
}
