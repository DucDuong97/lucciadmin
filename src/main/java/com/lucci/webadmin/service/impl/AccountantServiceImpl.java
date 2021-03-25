package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.AccountantService;
import com.lucci.webadmin.domain.Accountant;
import com.lucci.webadmin.repository.AccountantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Accountant}.
 */
@Service
@Transactional
public class AccountantServiceImpl implements AccountantService {

    private final Logger log = LoggerFactory.getLogger(AccountantServiceImpl.class);

    private final AccountantRepository accountantRepository;

    public AccountantServiceImpl(AccountantRepository accountantRepository) {
        this.accountantRepository = accountantRepository;
    }

    @Override
    public Accountant save(Accountant accountant) {
        log.debug("Request to save Accountant : {}", accountant);
        return accountantRepository.save(accountant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Accountant> findAll() {
        log.debug("Request to get all Accountants");
        return accountantRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Accountant> findOne(Long id) {
        log.debug("Request to get Accountant : {}", id);
        return accountantRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Accountant : {}", id);
        accountantRepository.deleteById(id);
    }
}
