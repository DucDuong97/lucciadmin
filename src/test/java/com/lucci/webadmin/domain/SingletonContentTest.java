package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class SingletonContentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SingletonContent.class);
        SingletonContent singletonContent1 = new SingletonContent();
        singletonContent1.setId(1L);
        SingletonContent singletonContent2 = new SingletonContent();
        singletonContent2.setId(singletonContent1.getId());
        assertThat(singletonContent1).isEqualTo(singletonContent2);
        singletonContent2.setId(2L);
        assertThat(singletonContent1).isNotEqualTo(singletonContent2);
        singletonContent1.setId(null);
        assertThat(singletonContent1).isNotEqualTo(singletonContent2);
    }
}
