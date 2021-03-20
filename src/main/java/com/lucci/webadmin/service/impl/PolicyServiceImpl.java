package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.PolicyService;
import com.lucci.webadmin.domain.Policy;
import com.lucci.webadmin.repository.PolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Policy}.
 */
@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {

    private final Logger log = LoggerFactory.getLogger(PolicyServiceImpl.class);

    private final PolicyRepository policyRepository;

    public PolicyServiceImpl(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public Policy save(Policy policy) {
        log.debug("Request to save Policy : {}", policy);
        return policyRepository.save(policy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Policy> findAll() {
        log.debug("Request to get all Policies");
        return policyRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Policy> findOne(Long id) {
        log.debug("Request to get Policy : {}", id);
        return policyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Policy : {}", id);
        policyRepository.deleteById(id);
    }
}
