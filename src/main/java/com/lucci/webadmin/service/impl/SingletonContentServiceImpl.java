package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.SingletonContentService;
import com.lucci.webadmin.domain.SingletonContent;
import com.lucci.webadmin.repository.SingletonContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SingletonContent}.
 */
@Service
@Transactional
public class SingletonContentServiceImpl implements SingletonContentService {

    private final Logger log = LoggerFactory.getLogger(SingletonContentServiceImpl.class);

    private final SingletonContentRepository singletonContentRepository;

    public SingletonContentServiceImpl(SingletonContentRepository singletonContentRepository) {
        this.singletonContentRepository = singletonContentRepository;
    }

    @Override
    public SingletonContent save(SingletonContent singletonContent) {
        log.debug("Request to save SingletonContent : {}", singletonContent);
        return singletonContentRepository.save(singletonContent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SingletonContent> findAll() {
        log.debug("Request to get all SingletonContents");
        return singletonContentRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SingletonContent> findOne(Long id) {
        log.debug("Request to get SingletonContent : {}", id);
        return singletonContentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SingletonContent : {}", id);
        singletonContentRepository.deleteById(id);
    }
}
