package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class MedicalRecordTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalRecord.class);
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setId(1L);
        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setId(medicalRecord1.getId());
        assertThat(medicalRecord1).isEqualTo(medicalRecord2);
        medicalRecord2.setId(2L);
        assertThat(medicalRecord1).isNotEqualTo(medicalRecord2);
        medicalRecord1.setId(null);
        assertThat(medicalRecord1).isNotEqualTo(medicalRecord2);
    }
}
