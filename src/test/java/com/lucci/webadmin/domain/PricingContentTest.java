package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class PricingContentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PricingContent.class);
        PricingContent pricingContent1 = new PricingContent();
        pricingContent1.setId(1L);
        PricingContent pricingContent2 = new PricingContent();
        pricingContent2.setId(pricingContent1.getId());
        assertThat(pricingContent1).isEqualTo(pricingContent2);
        pricingContent2.setId(2L);
        assertThat(pricingContent1).isNotEqualTo(pricingContent2);
        pricingContent1.setId(null);
        assertThat(pricingContent1).isNotEqualTo(pricingContent2);
    }
}
