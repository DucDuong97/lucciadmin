package com.lucci.webadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class PricingCardDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PricingCardDTO.class);
        PricingCardDTO pricingCardDTO1 = new PricingCardDTO();
        pricingCardDTO1.setId(1L);
        PricingCardDTO pricingCardDTO2 = new PricingCardDTO();
        assertThat(pricingCardDTO1).isNotEqualTo(pricingCardDTO2);
        pricingCardDTO2.setId(pricingCardDTO1.getId());
        assertThat(pricingCardDTO1).isEqualTo(pricingCardDTO2);
        pricingCardDTO2.setId(2L);
        assertThat(pricingCardDTO1).isNotEqualTo(pricingCardDTO2);
        pricingCardDTO1.setId(null);
        assertThat(pricingCardDTO1).isNotEqualTo(pricingCardDTO2);
    }
}
