package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class TreatmentHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TreatmentHistory.class);
        TreatmentHistory treatmentHistory1 = new TreatmentHistory();
        treatmentHistory1.setId(1L);
        TreatmentHistory treatmentHistory2 = new TreatmentHistory();
        treatmentHistory2.setId(treatmentHistory1.getId());
        assertThat(treatmentHistory1).isEqualTo(treatmentHistory2);
        treatmentHistory2.setId(2L);
        assertThat(treatmentHistory1).isNotEqualTo(treatmentHistory2);
        treatmentHistory1.setId(null);
        assertThat(treatmentHistory1).isNotEqualTo(treatmentHistory2);
    }
}
