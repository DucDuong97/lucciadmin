package com.lucci.webadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ImgUrlMapperTest {

    private ImgUrlMapper imgUrlMapper;

    @BeforeEach
    public void setUp() {
        imgUrlMapper = new ImgUrlMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(imgUrlMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(imgUrlMapper.fromId(null)).isNull();
    }
}
