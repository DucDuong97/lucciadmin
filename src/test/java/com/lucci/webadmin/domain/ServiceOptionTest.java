package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class ServiceOptionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOption.class);
        ServiceOption serviceOption1 = new ServiceOption();
        serviceOption1.setId(1L);
        ServiceOption serviceOption2 = new ServiceOption();
        serviceOption2.setId(serviceOption1.getId());
        assertThat(serviceOption1).isEqualTo(serviceOption2);
        serviceOption2.setId(2L);
        assertThat(serviceOption1).isNotEqualTo(serviceOption2);
        serviceOption1.setId(null);
        assertThat(serviceOption1).isNotEqualTo(serviceOption2);
    }
}
