package com.lucci.webadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PotentialMapperTest {

    private PotentialMapper potentialMapper;

    @BeforeEach
    public void setUp() {
        potentialMapper = new PotentialMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(potentialMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(potentialMapper.fromId(null)).isNull();
    }
}
