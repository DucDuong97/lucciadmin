package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.PricingContent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PricingContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PricingContentRepository extends JpaRepository<PricingContent, Long> {
}
