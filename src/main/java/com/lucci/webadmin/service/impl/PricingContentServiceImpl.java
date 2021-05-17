package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.PricingContentService;
import com.lucci.webadmin.domain.PricingContent;
import com.lucci.webadmin.repository.PricingContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PricingContent}.
 */
@Service
@Transactional
public class PricingContentServiceImpl implements PricingContentService {

    private final Logger log = LoggerFactory.getLogger(PricingContentServiceImpl.class);

    private final PricingContentRepository pricingContentRepository;

    public PricingContentServiceImpl(PricingContentRepository pricingContentRepository) {
        this.pricingContentRepository = pricingContentRepository;
    }

    @Override
    public PricingContent save(PricingContent pricingContent) {
        log.debug("Request to save PricingContent : {}", pricingContent);
        return pricingContentRepository.save(pricingContent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingContent> findAll() {
        log.debug("Request to get all PricingContents");
        return pricingContentRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PricingContent> findOne(Long id) {
        log.debug("Request to get PricingContent : {}", id);
        return pricingContentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PricingContent : {}", id);
        pricingContentRepository.deleteById(id);
    }
}
