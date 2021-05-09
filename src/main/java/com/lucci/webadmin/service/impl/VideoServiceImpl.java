package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.VideoService;
import com.lucci.webadmin.domain.Video;
import com.lucci.webadmin.repository.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Video}.
 */
@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    private final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);

    private final VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public Video save(Video video) {
        log.debug("Request to save Video : {}", video);
        return videoRepository.save(video);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Video> findAll() {
        log.debug("Request to get all Videos");
        return videoRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Video> findOne(Long id) {
        log.debug("Request to get Video : {}", id);
        return videoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Video : {}", id);
        videoRepository.deleteById(id);
    }
}
