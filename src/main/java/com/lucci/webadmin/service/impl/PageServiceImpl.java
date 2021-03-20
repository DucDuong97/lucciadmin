package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.PageService;
import com.lucci.webadmin.domain.Page;
import com.lucci.webadmin.repository.PageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Page}.
 */
@Service
@Transactional
public class PageServiceImpl implements PageService {

    private final Logger log = LoggerFactory.getLogger(PageServiceImpl.class);

    private final PageRepository pageRepository;

    public PageServiceImpl(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @Override
    public Page save(Page page) {
        log.debug("Request to save Page : {}", page);
        return pageRepository.save(page);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Page> findAll() {
        log.debug("Request to get all Pages");
        return pageRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Page> findOne(Long id) {
        log.debug("Request to get Page : {}", id);
        return pageRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Page : {}", id);
        pageRepository.deleteById(id);
    }
}
