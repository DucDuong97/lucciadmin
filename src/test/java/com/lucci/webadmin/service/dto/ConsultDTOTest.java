package com.lucci.webadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class ConsultDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultDTO.class);
        ConsultDTO consultDTO1 = new ConsultDTO();
        consultDTO1.setId(1L);
        ConsultDTO consultDTO2 = new ConsultDTO();
        assertThat(consultDTO1).isNotEqualTo(consultDTO2);
        consultDTO2.setId(consultDTO1.getId());
        assertThat(consultDTO1).isEqualTo(consultDTO2);
        consultDTO2.setId(2L);
        assertThat(consultDTO1).isNotEqualTo(consultDTO2);
        consultDTO1.setId(null);
        assertThat(consultDTO1).isNotEqualTo(consultDTO2);
    }
}
