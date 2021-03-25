package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class AccountantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accountant.class);
        Accountant accountant1 = new Accountant();
        accountant1.setId(1L);
        Accountant accountant2 = new Accountant();
        accountant2.setId(accountant1.getId());
        assertThat(accountant1).isEqualTo(accountant2);
        accountant2.setId(2L);
        assertThat(accountant1).isNotEqualTo(accountant2);
        accountant1.setId(null);
        assertThat(accountant1).isNotEqualTo(accountant2);
    }
}
