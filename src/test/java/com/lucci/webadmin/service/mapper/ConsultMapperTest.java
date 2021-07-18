package com.lucci.webadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsultMapperTest {

    private ConsultMapper consultMapper;

    @BeforeEach
    public void setUp() {
        consultMapper = new ConsultMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(consultMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(consultMapper.fromId(null)).isNull();
    }
}
