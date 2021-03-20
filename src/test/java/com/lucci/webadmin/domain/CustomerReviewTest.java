package com.lucci.webadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.lucci.webadmin.web.rest.TestUtil;

public class CustomerReviewTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerReview.class);
        CustomerReview customerReview1 = new CustomerReview();
        customerReview1.setId(1L);
        CustomerReview customerReview2 = new CustomerReview();
        customerReview2.setId(customerReview1.getId());
        assertThat(customerReview1).isEqualTo(customerReview2);
        customerReview2.setId(2L);
        assertThat(customerReview1).isNotEqualTo(customerReview2);
        customerReview1.setId(null);
        assertThat(customerReview1).isNotEqualTo(customerReview2);
    }
}
