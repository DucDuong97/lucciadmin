package com.lucci.webadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProcessMapperTest {

    private ProcessMapper processMapper;

    @BeforeEach
    public void setUp() {
        processMapper = new ProcessMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(processMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(processMapper.fromId(null)).isNull();
    }
}
