package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class TreatmentPlanTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TreatmentPlan.class);
        TreatmentPlan treatmentPlan1 = new TreatmentPlan();
        treatmentPlan1.setId(1L);
        TreatmentPlan treatmentPlan2 = new TreatmentPlan();
        treatmentPlan2.setId(treatmentPlan1.getId());
        assertThat(treatmentPlan1).isEqualTo(treatmentPlan2);
        treatmentPlan2.setId(2L);
        assertThat(treatmentPlan1).isNotEqualTo(treatmentPlan2);
        treatmentPlan1.setId(null);
        assertThat(treatmentPlan1).isNotEqualTo(treatmentPlan2);
    }
}
