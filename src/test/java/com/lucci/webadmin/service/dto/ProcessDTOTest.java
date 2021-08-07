package com.lucci.webadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class ProcessDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessDTO.class);
        ProcessDTO processDTO1 = new ProcessDTO();
        processDTO1.setId(1L);
        ProcessDTO processDTO2 = new ProcessDTO();
        assertThat(processDTO1).isNotEqualTo(processDTO2);
        processDTO2.setId(processDTO1.getId());
        assertThat(processDTO1).isEqualTo(processDTO2);
        processDTO2.setId(2L);
        assertThat(processDTO1).isNotEqualTo(processDTO2);
        processDTO1.setId(null);
        assertThat(processDTO1).isNotEqualTo(processDTO2);
    }
}
