package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.PricingContentService;
import com.lucci.webadmin.domain.PricingContent;
import com.lucci.webadmin.repository.PricingContentRepository;
import com.lucci.webadmin.service.dto.PricingContentDTO;
import com.lucci.webadmin.service.mapper.PricingContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PricingContent}.
 */
@Service
@Transactional
public class PricingContentServiceImpl implements PricingContentService {

    private final Logger log = LoggerFactory.getLogger(PricingContentServiceImpl.class);

    private final PricingContentRepository pricingContentRepository;

    private final PricingContentMapper pricingContentMapper;

    public PricingContentServiceImpl(PricingContentRepository pricingContentRepository, PricingContentMapper pricingContentMapper) {
        this.pricingContentRepository = pricingContentRepository;
        this.pricingContentMapper = pricingContentMapper;
    }

    @Override
    public PricingContentDTO save(PricingContentDTO pricingContentDTO) {
        log.debug("Request to save PricingContent : {}", pricingContentDTO);
        PricingContent pricingContent = pricingContentMapper.toEntity(pricingContentDTO);
        pricingContent = pricingContentRepository.save(pricingContent);
        return pricingContentMapper.toDto(pricingContent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingContentDTO> findAll() {
        log.debug("Request to get all PricingContents");
        return pricingContentRepository.findAll().stream()
            .map(pricingContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PricingContentDTO> findOne(Long id) {
        log.debug("Request to get PricingContent : {}", id);
        return pricingContentRepository.findById(id)
            .map(pricingContentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PricingContent : {}", id);
        pricingContentRepository.deleteById(id);
    }
}
