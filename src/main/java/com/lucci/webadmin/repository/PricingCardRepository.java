package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.PricingCard;

import com.lucci.webadmin.repository.custom.CustomPricingCardRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PricingCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PricingCardRepository extends JpaRepository<PricingCard, Long>, CustomPricingCardRepository {
}
