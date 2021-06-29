package com.lucci.webadmin.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingMapperTest {

    @Autowired
    private BookingMapper bookingMapper;

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bookingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bookingMapper.fromId(null)).isNull();
    }
}
