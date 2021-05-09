package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.ServiceOptionService;
import com.lucci.webadmin.domain.ServiceOption;
import com.lucci.webadmin.repository.ServiceOptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceOption}.
 */
@Service
@Transactional
public class ServiceOptionServiceImpl implements ServiceOptionService {

    private final Logger log = LoggerFactory.getLogger(ServiceOptionServiceImpl.class);

    private final ServiceOptionRepository serviceOptionRepository;

    public ServiceOptionServiceImpl(ServiceOptionRepository serviceOptionRepository) {
        this.serviceOptionRepository = serviceOptionRepository;
    }

    @Override
    public ServiceOption save(ServiceOption serviceOption) {
        log.debug("Request to save ServiceOption : {}", serviceOption);
        return serviceOptionRepository.save(serviceOption);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceOption> findAll() {
        log.debug("Request to get all ServiceOptions");
        return serviceOptionRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOption> findOne(Long id) {
        log.debug("Request to get ServiceOption : {}", id);
        return serviceOptionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceOption : {}", id);
        serviceOptionRepository.deleteById(id);
    }
}
