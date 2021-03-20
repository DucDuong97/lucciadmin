package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.Policy;
import com.lucci.webadmin.repository.PolicyRepository;
import com.lucci.webadmin.service.PolicyService;

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
 * Integration tests for the {@link PolicyResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PolicyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPolicyMockMvc;

    private Policy policy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Policy createEntity(EntityManager em) {
        Policy policy = new Policy()
            .name(DEFAULT_NAME)
            .content(DEFAULT_CONTENT);
        return policy;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Policy createUpdatedEntity(EntityManager em) {
        Policy policy = new Policy()
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT);
        return policy;
    }

    @BeforeEach
    public void initTest() {
        policy = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicy() throws Exception {
        int databaseSizeBeforeCreate = policyRepository.findAll().size();
        // Create the Policy
        restPolicyMockMvc.perform(post("/api/policies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isCreated());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate + 1);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPolicy.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createPolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyRepository.findAll().size();

        // Create the Policy with an existing ID
        policy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyMockMvc.perform(post("/api/policies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setName(null);

        // Create the Policy, which fails.


        restPolicyMockMvc.perform(post("/api/policies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setContent(null);

        // Create the Policy, which fails.


        restPolicyMockMvc.perform(post("/api/policies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPolicies() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList
        restPolicyMockMvc.perform(get("/api/policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policy.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
    
    @Test
    @Transactional
    public void getPolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get the policy
        restPolicyMockMvc.perform(get("/api/policies/{id}", policy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(policy.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }
    @Test
    @Transactional
    public void getNonExistingPolicy() throws Exception {
        // Get the policy
        restPolicyMockMvc.perform(get("/api/policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicy() throws Exception {
        // Initialize the database
        policyService.save(policy);

        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy
        Policy updatedPolicy = policyRepository.findById(policy.getId()).get();
        // Disconnect from session so that the updates on updatedPolicy are not directly saved in db
        em.detach(updatedPolicy);
        updatedPolicy
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT);

        restPolicyMockMvc.perform(put("/api/policies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPolicy)))
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPolicy.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyMockMvc.perform(put("/api/policies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePolicy() throws Exception {
        // Initialize the database
        policyService.save(policy);

        int databaseSizeBeforeDelete = policyRepository.findAll().size();

        // Delete the policy
        restPolicyMockMvc.perform(delete("/api/policies/{id}", policy.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
