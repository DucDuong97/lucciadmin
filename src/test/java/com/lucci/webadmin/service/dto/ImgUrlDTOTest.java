package com.lucci.webadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class ImgUrlDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImgUrlDTO.class);
        ImgUrlDTO imgUrlDTO1 = new ImgUrlDTO();
        imgUrlDTO1.setId(1L);
        ImgUrlDTO imgUrlDTO2 = new ImgUrlDTO();
        assertThat(imgUrlDTO1).isNotEqualTo(imgUrlDTO2);
        imgUrlDTO2.setId(imgUrlDTO1.getId());
        assertThat(imgUrlDTO1).isEqualTo(imgUrlDTO2);
        imgUrlDTO2.setId(2L);
        assertThat(imgUrlDTO1).isNotEqualTo(imgUrlDTO2);
        imgUrlDTO1.setId(null);
        assertThat(imgUrlDTO1).isNotEqualTo(imgUrlDTO2);
    }
}
