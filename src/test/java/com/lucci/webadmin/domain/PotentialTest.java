package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class PotentialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Potential.class);
        Potential potential1 = new Potential();
        potential1.setId(1L);
        Potential potential2 = new Potential();
        potential2.setId(potential1.getId());
        assertThat(potential1).isEqualTo(potential2);
        potential2.setId(2L);
        assertThat(potential1).isNotEqualTo(potential2);
        potential1.setId(null);
        assertThat(potential1).isNotEqualTo(potential2);
    }
}
