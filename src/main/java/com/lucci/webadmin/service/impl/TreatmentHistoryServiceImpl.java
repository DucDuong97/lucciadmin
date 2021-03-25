package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.TreatmentHistoryService;
import com.lucci.webadmin.domain.TreatmentHistory;
import com.lucci.webadmin.repository.TreatmentHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TreatmentHistory}.
 */
@Service
@Transactional
public class TreatmentHistoryServiceImpl implements TreatmentHistoryService {

    private final Logger log = LoggerFactory.getLogger(TreatmentHistoryServiceImpl.class);

    private final TreatmentHistoryRepository treatmentHistoryRepository;

    public TreatmentHistoryServiceImpl(TreatmentHistoryRepository treatmentHistoryRepository) {
        this.treatmentHistoryRepository = treatmentHistoryRepository;
    }

    @Override
    public TreatmentHistory save(TreatmentHistory treatmentHistory) {
        log.debug("Request to save TreatmentHistory : {}", treatmentHistory);
        return treatmentHistoryRepository.save(treatmentHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TreatmentHistory> findAll() {
        log.debug("Request to get all TreatmentHistories");
        return treatmentHistoryRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TreatmentHistory> findOne(Long id) {
        log.debug("Request to get TreatmentHistory : {}", id);
        return treatmentHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TreatmentHistory : {}", id);
        treatmentHistoryRepository.deleteById(id);
    }
}
