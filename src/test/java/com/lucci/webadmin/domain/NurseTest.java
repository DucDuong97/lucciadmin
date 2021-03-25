package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class NurseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nurse.class);
        Nurse nurse1 = new Nurse();
        nurse1.setId(1L);
        Nurse nurse2 = new Nurse();
        nurse2.setId(nurse1.getId());
        assertThat(nurse1).isEqualTo(nurse2);
        nurse2.setId(2L);
        assertThat(nurse1).isNotEqualTo(nurse2);
        nurse1.setId(null);
        assertThat(nurse1).isNotEqualTo(nurse2);
    }
}
