package com.lucci.webadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PricingCardMapperTest {

    private PricingCardMapper pricingCardMapper;

    @BeforeEach
    public void setUp() {
        pricingCardMapper = new PricingCardMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pricingCardMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pricingCardMapper.fromId(null)).isNull();
    }
}
