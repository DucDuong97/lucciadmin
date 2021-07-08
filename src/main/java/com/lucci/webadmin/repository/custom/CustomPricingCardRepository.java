package com.lucci.webadmin.repository.custom;

import com.lucci.webadmin.domain.PricingCard;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomPricingCardRepository {
    @Query(value = "SELECT * FROM pricing_card WHERE service_item_id=?1", nativeQuery = true)
    List<PricingCard> findAllByServiceId(Long id);
}
