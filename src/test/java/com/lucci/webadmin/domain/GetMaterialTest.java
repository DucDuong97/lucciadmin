package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class GetMaterialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GetMaterial.class);
        GetMaterial getMaterial1 = new GetMaterial();
        getMaterial1.setId(1L);
        GetMaterial getMaterial2 = new GetMaterial();
        getMaterial2.setId(getMaterial1.getId());
        assertThat(getMaterial1).isEqualTo(getMaterial2);
        getMaterial2.setId(2L);
        assertThat(getMaterial1).isNotEqualTo(getMaterial2);
        getMaterial1.setId(null);
        assertThat(getMaterial1).isNotEqualTo(getMaterial2);
    }
}
