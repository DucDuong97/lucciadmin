package com.lucci.webadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class TreatmentPlanDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TreatmentPlanDTO.class);
        TreatmentPlanDTO treatmentPlanDTO1 = new TreatmentPlanDTO();
        treatmentPlanDTO1.setId(1L);
        TreatmentPlanDTO treatmentPlanDTO2 = new TreatmentPlanDTO();
        assertThat(treatmentPlanDTO1).isNotEqualTo(treatmentPlanDTO2);
        treatmentPlanDTO2.setId(treatmentPlanDTO1.getId());
        assertThat(treatmentPlanDTO1).isEqualTo(treatmentPlanDTO2);
        treatmentPlanDTO2.setId(2L);
        assertThat(treatmentPlanDTO1).isNotEqualTo(treatmentPlanDTO2);
        treatmentPlanDTO1.setId(null);
        assertThat(treatmentPlanDTO1).isNotEqualTo(treatmentPlanDTO2);
    }
}
