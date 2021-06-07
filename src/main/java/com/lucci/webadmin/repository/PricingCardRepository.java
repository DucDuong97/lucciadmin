package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.PricingCard;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PricingCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PricingCardRepository extends JpaRepository<PricingCard, Long>, CustomPricingCardRepository {
}
