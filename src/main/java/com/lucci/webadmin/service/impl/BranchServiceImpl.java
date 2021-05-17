package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.BranchService;
import com.lucci.webadmin.domain.Branch;
import com.lucci.webadmin.repository.BranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Branch}.
 */
@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    private final Logger log = LoggerFactory.getLogger(BranchServiceImpl.class);

    private final BranchRepository branchRepository;

    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public Branch save(Branch branch) {
        log.debug("Request to save Branch : {}", branch);
        return branchRepository.save(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Branch> findAll() {
        log.debug("Request to get all Branches");
        return branchRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Branch> findOne(Long id) {
        log.debug("Request to get Branch : {}", id);
        return branchRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Branch : {}", id);
        branchRepository.deleteById(id);
    }
}
