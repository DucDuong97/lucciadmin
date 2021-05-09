package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.CustomerReview;
import com.lucci.webadmin.repository.CustomerReviewRepository;
import com.lucci.webadmin.service.CustomerReviewService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerReviewResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerReviewResourceIT {

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private CustomerReviewRepository customerReviewRepository;

    @Autowired
    private CustomerReviewService customerReviewService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerReviewMockMvc;

    private CustomerReview customerReview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerReview createEntity(EntityManager em) {
        CustomerReview customerReview = new CustomerReview()
            .customerName(DEFAULT_CUSTOMER_NAME)
            .customerAddress(DEFAULT_CUSTOMER_ADDRESS)
            .comment(DEFAULT_COMMENT);
        return customerReview;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerReview createUpdatedEntity(EntityManager em) {
        CustomerReview customerReview = new CustomerReview()
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerAddress(UPDATED_CUSTOMER_ADDRESS)
            .comment(UPDATED_COMMENT);
        return customerReview;
    }

    @BeforeEach
    public void initTest() {
        customerReview = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerReview() throws Exception {
        int databaseSizeBeforeCreate = customerReviewRepository.findAll().size();
        // Create the CustomerReview
        restCustomerReviewMockMvc.perform(post("/api/customer-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerReview)))
            .andExpect(status().isCreated());

        // Validate the CustomerReview in the database
        List<CustomerReview> customerReviewList = customerReviewRepository.findAll();
        assertThat(customerReviewList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerReview testCustomerReview = customerReviewList.get(customerReviewList.size() - 1);
        assertThat(testCustomerReview.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testCustomerReview.getCustomerAddress()).isEqualTo(DEFAULT_CUSTOMER_ADDRESS);
        assertThat(testCustomerReview.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createCustomerReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerReviewRepository.findAll().size();

        // Create the CustomerReview with an existing ID
        customerReview.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerReviewMockMvc.perform(post("/api/customer-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerReview)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerReview in the database
        List<CustomerReview> customerReviewList = customerReviewRepository.findAll();
        assertThat(customerReviewList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCustomerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerReviewRepository.findAll().size();
        // set the field null
        customerReview.setCustomerName(null);

        // Create the CustomerReview, which fails.


        restCustomerReviewMockMvc.perform(post("/api/customer-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerReview)))
            .andExpect(status().isBadRequest());

        List<CustomerReview> customerReviewList = customerReviewRepository.findAll();
        assertThat(customerReviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerReviewRepository.findAll().size();
        // set the field null
        customerReview.setComment(null);

        // Create the CustomerReview, which fails.


        restCustomerReviewMockMvc.perform(post("/api/customer-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerReview)))
            .andExpect(status().isBadRequest());

        List<CustomerReview> customerReviewList = customerReviewRepository.findAll();
        assertThat(customerReviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerReviews() throws Exception {
        // Initialize the database
        customerReviewRepository.saveAndFlush(customerReview);

        // Get all the customerReviewList
        restCustomerReviewMockMvc.perform(get("/api/customer-reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].customerAddress").value(hasItem(DEFAULT_CUSTOMER_ADDRESS)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }
    
    @Test
    @Transactional
    public void getCustomerReview() throws Exception {
        // Initialize the database
        customerReviewRepository.saveAndFlush(customerReview);

        // Get the customerReview
        restCustomerReviewMockMvc.perform(get("/api/customer-reviews/{id}", customerReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerReview.getId().intValue()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.customerAddress").value(DEFAULT_CUSTOMER_ADDRESS))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerReview() throws Exception {
        // Get the customerReview
        restCustomerReviewMockMvc.perform(get("/api/customer-reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerReview() throws Exception {
        // Initialize the database
        customerReviewService.save(customerReview);

        int databaseSizeBeforeUpdate = customerReviewRepository.findAll().size();

        // Update the customerReview
        CustomerReview updatedCustomerReview = customerReviewRepository.findById(customerReview.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerReview are not directly saved in db
        em.detach(updatedCustomerReview);
        updatedCustomerReview
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerAddress(UPDATED_CUSTOMER_ADDRESS)
            .comment(UPDATED_COMMENT);

        restCustomerReviewMockMvc.perform(put("/api/customer-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerReview)))
            .andExpect(status().isOk());

        // Validate the CustomerReview in the database
        List<CustomerReview> customerReviewList = customerReviewRepository.findAll();
        assertThat(customerReviewList).hasSize(databaseSizeBeforeUpdate);
        CustomerReview testCustomerReview = customerReviewList.get(customerReviewList.size() - 1);
        assertThat(testCustomerReview.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testCustomerReview.getCustomerAddress()).isEqualTo(UPDATED_CUSTOMER_ADDRESS);
        assertThat(testCustomerReview.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerReview() throws Exception {
        int databaseSizeBeforeUpdate = customerReviewRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerReviewMockMvc.perform(put("/api/customer-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerReview)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerReview in the database
        List<CustomerReview> customerReviewList = customerReviewRepository.findAll();
        assertThat(customerReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerReview() throws Exception {
        // Initialize the database
        customerReviewService.save(customerReview);

        int databaseSizeBeforeDelete = customerReviewRepository.findAll().size();

        // Delete the customerReview
        restCustomerReviewMockMvc.perform(delete("/api/customer-reviews/{id}", customerReview.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerReview> customerReviewList = customerReviewRepository.findAll();
        assertThat(customerReviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
