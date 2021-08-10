package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.PricingCardService;
import com.lucci.webadmin.domain.PricingCard;
import com.lucci.webadmin.repository.PricingCardRepository;
import com.lucci.webadmin.service.dto.PricingCardDTO;
import com.lucci.webadmin.service.mapper.PricingCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PricingCard}.
 */
@Service
@Transactional
public class PricingCardServiceImpl implements PricingCardService {

    private final Logger log = LoggerFactory.getLogger(PricingCardServiceImpl.class);

    private final PricingCardRepository pricingCardRepository;

    private final PricingCardMapper pricingCardMapper;

    public PricingCardServiceImpl(PricingCardRepository pricingCardRepository, PricingCardMapper pricingCardMapper) {
        this.pricingCardRepository = pricingCardRepository;
        this.pricingCardMapper = pricingCardMapper;
    }

    @Override
    public PricingCardDTO save(PricingCardDTO pricingCardDTO) {
        log.debug("Request to save PricingCard : {}", pricingCardDTO);
        PricingCard pricingCard = pricingCardMapper.toEntity(pricingCardDTO);
        pricingCard = pricingCardRepository.save(pricingCard);
        return pricingCardMapper.toDto(pricingCard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingCardDTO> findAll() {
        log.debug("Request to get all PricingCards");
        return pricingCardRepository.findAll().stream()
            .map(pricingCardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<PricingCardDTO> findAllByServiceId(Long id) {
        log.debug("Request to get all PricingCards");
        return pricingCardRepository.findByServiceItemId(id).stream()
            .map(pricingCardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PricingCardDTO> findOne(Long id) {
        log.debug("Request to get PricingCard : {}", id);
        return pricingCardRepository.findById(id)
            .map(pricingCardMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PricingCard : {}", id);
        pricingCardRepository.deleteById(id);
    }
}
