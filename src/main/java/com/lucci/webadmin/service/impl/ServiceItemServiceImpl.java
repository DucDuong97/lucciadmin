package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.ServiceItemService;
import com.lucci.webadmin.domain.ServiceItem;
import com.lucci.webadmin.repository.ServiceItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceItem}.
 */
@Service
@Transactional
public class ServiceItemServiceImpl implements ServiceItemService {

    private final Logger log = LoggerFactory.getLogger(ServiceItemServiceImpl.class);

    private final ServiceItemRepository serviceItemRepository;

    public ServiceItemServiceImpl(ServiceItemRepository serviceItemRepository) {
        this.serviceItemRepository = serviceItemRepository;
    }

    @Override
    public ServiceItem save(ServiceItem serviceItem) {
        log.debug("Request to save ServiceItem : {}", serviceItem);
        return serviceItemRepository.save(serviceItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceItem> findAll() {
        log.debug("Request to get all ServiceItems");
        return serviceItemRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceItem> findOne(Long id) {
        log.debug("Request to get ServiceItem : {}", id);
        return serviceItemRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceItem : {}", id);
        serviceItemRepository.deleteById(id);
    }
}
