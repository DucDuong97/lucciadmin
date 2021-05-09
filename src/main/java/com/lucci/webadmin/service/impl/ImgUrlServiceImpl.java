package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.ImgUrlService;
import com.lucci.webadmin.domain.ImgUrl;
import com.lucci.webadmin.repository.ImgUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ImgUrl}.
 */
@Service
@Transactional
public class ImgUrlServiceImpl implements ImgUrlService {

    private final Logger log = LoggerFactory.getLogger(ImgUrlServiceImpl.class);

    private final ImgUrlRepository imgUrlRepository;

    public ImgUrlServiceImpl(ImgUrlRepository imgUrlRepository) {
        this.imgUrlRepository = imgUrlRepository;
    }

    @Override
    public ImgUrl save(ImgUrl imgUrl) {
        log.debug("Request to save ImgUrl : {}", imgUrl);
        return imgUrlRepository.save(imgUrl);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImgUrl> findAll() {
        log.debug("Request to get all ImgUrls");
        return imgUrlRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ImgUrl> findOne(Long id) {
        log.debug("Request to get ImgUrl : {}", id);
        return imgUrlRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImgUrl : {}", id);
        imgUrlRepository.deleteById(id);
    }
}
