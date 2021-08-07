package com.lucci.webadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PricingContentMapperTest {

    private PricingContentMapper pricingContentMapper;

    @BeforeEach
    public void setUp() {
        pricingContentMapper = new PricingContentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pricingContentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pricingContentMapper.fromId(null)).isNull();
    }
}
