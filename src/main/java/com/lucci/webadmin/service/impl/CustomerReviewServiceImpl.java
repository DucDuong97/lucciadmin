package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.CustomerReviewService;
import com.lucci.webadmin.domain.CustomerReview;
import com.lucci.webadmin.repository.CustomerReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CustomerReview}.
 */
@Service
@Transactional
public class CustomerReviewServiceImpl implements CustomerReviewService {

    private final Logger log = LoggerFactory.getLogger(CustomerReviewServiceImpl.class);

    private final CustomerReviewRepository customerReviewRepository;

    public CustomerReviewServiceImpl(CustomerReviewRepository customerReviewRepository) {
        this.customerReviewRepository = customerReviewRepository;
    }

    @Override
    public CustomerReview save(CustomerReview customerReview) {
        log.debug("Request to save CustomerReview : {}", customerReview);
        return customerReviewRepository.save(customerReview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerReview> findAll() {
        log.debug("Request to get all CustomerReviews");
        return customerReviewRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerReview> findOne(Long id) {
        log.debug("Request to get CustomerReview : {}", id);
        return customerReviewRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerReview : {}", id);
        customerReviewRepository.deleteById(id);
    }
}
