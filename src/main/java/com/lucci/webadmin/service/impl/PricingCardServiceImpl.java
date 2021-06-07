package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.PricingCardService;
import com.lucci.webadmin.domain.PricingCard;
import com.lucci.webadmin.repository.PricingCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PricingCard}.
 */
@Service
@Transactional
public class PricingCardServiceImpl implements PricingCardService {

    private final Logger log = LoggerFactory.getLogger(PricingCardServiceImpl.class);

    private final PricingCardRepository pricingCardRepository;

    public PricingCardServiceImpl(PricingCardRepository pricingCardRepository) {
        this.pricingCardRepository = pricingCardRepository;
    }

    @Override
    public PricingCard save(PricingCard pricingCard) {
        log.debug("Request to save PricingCard : {}", pricingCard);
        return pricingCardRepository.save(pricingCard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingCard> findAll() {
        log.debug("Request to get all PricingCards");
        return pricingCardRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingCard> findAllByServiceId(Long id) {
        log.debug("Request to get all PricingCards by Service ID: {}", id);
        return pricingCardRepository.findAllByServiceId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PricingCard> findOne(Long id) {
        log.debug("Request to get PricingCard : {}", id);
        return pricingCardRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PricingCard : {}", id);
        pricingCardRepository.deleteById(id);
    }
}
