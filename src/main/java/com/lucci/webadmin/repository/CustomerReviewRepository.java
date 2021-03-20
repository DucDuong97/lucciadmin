package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.CustomerReview;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long> {
}
