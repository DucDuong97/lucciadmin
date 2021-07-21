package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.ServiceItemService;
import com.lucci.webadmin.domain.ServiceItem;
import com.lucci.webadmin.repository.ServiceItemRepository;
import com.lucci.webadmin.service.dto.ServiceItemDTO;
import com.lucci.webadmin.service.mapper.ServiceItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ServiceItem}.
 */
@Service
@Transactional
public class ServiceItemServiceImpl implements ServiceItemService {

    private final Logger log = LoggerFactory.getLogger(ServiceItemServiceImpl.class);

    private final ServiceItemRepository serviceItemRepository;

    private final ServiceItemMapper serviceItemMapper;

    public ServiceItemServiceImpl(ServiceItemRepository serviceItemRepository, ServiceItemMapper serviceItemMapper) {
        this.serviceItemRepository = serviceItemRepository;
        this.serviceItemMapper = serviceItemMapper;
    }

    @Override
    public ServiceItemDTO save(ServiceItemDTO serviceItemDTO) {
        log.debug("Request to save ServiceItem : {}", serviceItemDTO);
        ServiceItem serviceItem = serviceItemMapper.toEntity(serviceItemDTO);
        serviceItem = serviceItemRepository.save(serviceItem);
        return serviceItemMapper.toDto(serviceItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceItemDTO> findAll() {
        log.debug("Request to get all ServiceItems");
        return serviceItemRepository.findAllWithEagerRelationships().stream()
            .map(serviceItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    public Page<ServiceItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return serviceItemRepository.findAllWithEagerRelationships(pageable).map(serviceItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceItemDTO> findOne(Long id) {
        log.debug("Request to get ServiceItem : {}", id);
        return serviceItemRepository.findOneWithEagerRelationships(id)
            .map(serviceItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceItem : {}", id);
        serviceItemRepository.deleteById(id);
    }
}
