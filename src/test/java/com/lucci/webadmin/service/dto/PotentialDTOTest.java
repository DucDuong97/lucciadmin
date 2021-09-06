package com.lucci.webadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class PotentialDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PotentialDTO.class);
        PotentialDTO potentialDTO1 = new PotentialDTO();
        potentialDTO1.setId(1L);
        PotentialDTO potentialDTO2 = new PotentialDTO();
        assertThat(potentialDTO1).isNotEqualTo(potentialDTO2);
        potentialDTO2.setId(potentialDTO1.getId());
        assertThat(potentialDTO1).isEqualTo(potentialDTO2);
        potentialDTO2.setId(2L);
        assertThat(potentialDTO1).isNotEqualTo(potentialDTO2);
        potentialDTO1.setId(null);
        assertThat(potentialDTO1).isNotEqualTo(potentialDTO2);
    }
}
