package com.lucci.webadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TreatmentPlanMapperTest {

    private TreatmentPlanMapper treatmentPlanMapper;

    @BeforeEach
    public void setUp() {
        treatmentPlanMapper = new TreatmentPlanMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(treatmentPlanMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(treatmentPlanMapper.fromId(null)).isNull();
    }
}
