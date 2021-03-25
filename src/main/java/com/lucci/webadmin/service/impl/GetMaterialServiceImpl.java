package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.GetMaterialService;
import com.lucci.webadmin.domain.GetMaterial;
import com.lucci.webadmin.repository.GetMaterialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link GetMaterial}.
 */
@Service
@Transactional
public class GetMaterialServiceImpl implements GetMaterialService {

    private final Logger log = LoggerFactory.getLogger(GetMaterialServiceImpl.class);

    private final GetMaterialRepository getMaterialRepository;

    public GetMaterialServiceImpl(GetMaterialRepository getMaterialRepository) {
        this.getMaterialRepository = getMaterialRepository;
    }

    @Override
    public GetMaterial save(GetMaterial getMaterial) {
        log.debug("Request to save GetMaterial : {}", getMaterial);
        return getMaterialRepository.save(getMaterial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetMaterial> findAll() {
        log.debug("Request to get all GetMaterials");
        return getMaterialRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GetMaterial> findOne(Long id) {
        log.debug("Request to get GetMaterial : {}", id);
        return getMaterialRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GetMaterial : {}", id);
        getMaterialRepository.deleteById(id);
    }
}
