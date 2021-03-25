package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.TreatmentService;
import com.lucci.webadmin.domain.Treatment;
import com.lucci.webadmin.repository.TreatmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Treatment}.
 */
@Service
@Transactional
public class TreatmentServiceImpl implements TreatmentService {

    private final Logger log = LoggerFactory.getLogger(TreatmentServiceImpl.class);

    private final TreatmentRepository treatmentRepository;

    public TreatmentServiceImpl(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    public Treatment save(Treatment treatment) {
        log.debug("Request to save Treatment : {}", treatment);
        return treatmentRepository.save(treatment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Treatment> findAll() {
        log.debug("Request to get all Treatments");
        return treatmentRepository.findAllWithEagerRelationships();
    }


    public Page<Treatment> findAllWithEagerRelationships(Pageable pageable) {
        return treatmentRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Treatment> findOne(Long id) {
        log.debug("Request to get Treatment : {}", id);
        return treatmentRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Treatment : {}", id);
        treatmentRepository.deleteById(id);
    }
}
