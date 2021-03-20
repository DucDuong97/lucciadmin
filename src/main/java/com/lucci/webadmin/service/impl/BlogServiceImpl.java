package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.BlogService;
import com.lucci.webadmin.domain.Blog;
import com.lucci.webadmin.repository.BlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Blog}.
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogServiceImpl.class);

    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Blog save(Blog blog) {
        log.debug("Request to save Blog : {}", blog);
        return blogRepository.save(blog);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Blog> findAll() {
        log.debug("Request to get all Blogs");
        return blogRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Blog> findOne(Long id) {
        log.debug("Request to get Blog : {}", id);
        return blogRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Blog : {}", id);
        blogRepository.deleteById(id);
    }
}
