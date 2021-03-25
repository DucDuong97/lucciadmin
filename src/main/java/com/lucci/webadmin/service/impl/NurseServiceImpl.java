package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.NurseService;
import com.lucci.webadmin.domain.Nurse;
import com.lucci.webadmin.repository.NurseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Nurse}.
 */
@Service
@Transactional
public class NurseServiceImpl implements NurseService {

    private final Logger log = LoggerFactory.getLogger(NurseServiceImpl.class);

    private final NurseRepository nurseRepository;

    public NurseServiceImpl(NurseRepository nurseRepository) {
        this.nurseRepository = nurseRepository;
    }

    @Override
    public Nurse save(Nurse nurse) {
        log.debug("Request to save Nurse : {}", nurse);
        return nurseRepository.save(nurse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nurse> findAll() {
        log.debug("Request to get all Nurses");
        return nurseRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Nurse> findOne(Long id) {
        log.debug("Request to get Nurse : {}", id);
        return nurseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nurse : {}", id);
        nurseRepository.deleteById(id);
    }
}
