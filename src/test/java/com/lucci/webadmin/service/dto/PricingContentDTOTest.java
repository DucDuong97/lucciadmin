package com.lucci.webadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class PricingContentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PricingContentDTO.class);
        PricingContentDTO pricingContentDTO1 = new PricingContentDTO();
        pricingContentDTO1.setId(1L);
        PricingContentDTO pricingContentDTO2 = new PricingContentDTO();
        assertThat(pricingContentDTO1).isNotEqualTo(pricingContentDTO2);
        pricingContentDTO2.setId(pricingContentDTO1.getId());
        assertThat(pricingContentDTO1).isEqualTo(pricingContentDTO2);
        pricingContentDTO2.setId(2L);
        assertThat(pricingContentDTO1).isNotEqualTo(pricingContentDTO2);
        pricingContentDTO1.setId(null);
        assertThat(pricingContentDTO1).isNotEqualTo(pricingContentDTO2);
    }
}
