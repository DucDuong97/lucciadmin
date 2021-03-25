package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.ReceptionistService;
import com.lucci.webadmin.domain.Receptionist;
import com.lucci.webadmin.repository.ReceptionistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Receptionist}.
 */
@Service
@Transactional
public class ReceptionistServiceImpl implements ReceptionistService {

    private final Logger log = LoggerFactory.getLogger(ReceptionistServiceImpl.class);

    private final ReceptionistRepository receptionistRepository;

    public ReceptionistServiceImpl(ReceptionistRepository receptionistRepository) {
        this.receptionistRepository = receptionistRepository;
    }

    @Override
    public Receptionist save(Receptionist receptionist) {
        log.debug("Request to save Receptionist : {}", receptionist);
        return receptionistRepository.save(receptionist);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Receptionist> findAll() {
        log.debug("Request to get all Receptionists");
        return receptionistRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Receptionist> findOne(Long id) {
        log.debug("Request to get Receptionist : {}", id);
        return receptionistRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Receptionist : {}", id);
        receptionistRepository.deleteById(id);
    }
}
