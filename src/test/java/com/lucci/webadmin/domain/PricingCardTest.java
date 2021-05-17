package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class PricingCardTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PricingCard.class);
        PricingCard pricingCard1 = new PricingCard();
        pricingCard1.setId(1L);
        PricingCard pricingCard2 = new PricingCard();
        pricingCard2.setId(pricingCard1.getId());
        assertThat(pricingCard1).isEqualTo(pricingCard2);
        pricingCard2.setId(2L);
        assertThat(pricingCard1).isNotEqualTo(pricingCard2);
        pricingCard1.setId(null);
        assertThat(pricingCard1).isNotEqualTo(pricingCard2);
    }
}
