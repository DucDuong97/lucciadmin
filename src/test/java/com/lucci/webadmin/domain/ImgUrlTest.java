package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class ImgUrlTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImgUrl.class);
        ImgUrl imgUrl1 = new ImgUrl();
        imgUrl1.setId(1L);
        ImgUrl imgUrl2 = new ImgUrl();
        imgUrl2.setId(imgUrl1.getId());
        assertThat(imgUrl1).isEqualTo(imgUrl2);
        imgUrl2.setId(2L);
        assertThat(imgUrl1).isNotEqualTo(imgUrl2);
        imgUrl1.setId(null);
        assertThat(imgUrl1).isNotEqualTo(imgUrl2);
    }
}
