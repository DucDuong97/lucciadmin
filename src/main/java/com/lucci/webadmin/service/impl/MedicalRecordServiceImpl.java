package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.MedicalRecordService;
import com.lucci.webadmin.domain.MedicalRecord;
import com.lucci.webadmin.repository.MedicalRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MedicalRecord}.
 */
@Service
@Transactional
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final Logger log = LoggerFactory.getLogger(MedicalRecordServiceImpl.class);

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public MedicalRecord save(MedicalRecord medicalRecord) {
        log.debug("Request to save MedicalRecord : {}", medicalRecord);
        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalRecord> findAll() {
        log.debug("Request to get all MedicalRecords");
        return medicalRecordRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalRecord> findOne(Long id) {
        log.debug("Request to get MedicalRecord : {}", id);
        return medicalRecordRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalRecord : {}", id);
        medicalRecordRepository.deleteById(id);
    }
}
