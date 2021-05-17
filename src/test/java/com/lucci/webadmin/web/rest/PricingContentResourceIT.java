package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.PricingContent;
import com.lucci.webadmin.repository.PricingContentRepository;
import com.lucci.webadmin.service.PricingContentService;

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
 * Integration tests for the {@link PricingContentResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PricingContentResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRO = false;
    private static final Boolean UPDATED_PRO = true;

    @Autowired
    private PricingContentRepository pricingContentRepository;

    @Autowired
    private PricingContentService pricingContentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPricingContentMockMvc;

    private PricingContent pricingContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PricingContent createEntity(EntityManager em) {
        PricingContent pricingContent = new PricingContent()
            .content(DEFAULT_CONTENT)
            .pro(DEFAULT_PRO);
        return pricingContent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PricingContent createUpdatedEntity(EntityManager em) {
        PricingContent pricingContent = new PricingContent()
            .content(UPDATED_CONTENT)
            .pro(UPDATED_PRO);
        return pricingContent;
    }

    @BeforeEach
    public void initTest() {
        pricingContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createPricingContent() throws Exception {
        int databaseSizeBeforeCreate = pricingContentRepository.findAll().size();
        // Create the PricingContent
        restPricingContentMockMvc.perform(post("/api/pricing-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pricingContent)))
            .andExpect(status().isCreated());

        // Validate the PricingContent in the database
        List<PricingContent> pricingContentList = pricingContentRepository.findAll();
        assertThat(pricingContentList).hasSize(databaseSizeBeforeCreate + 1);
        PricingContent testPricingContent = pricingContentList.get(pricingContentList.size() - 1);
        assertThat(testPricingContent.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPricingContent.isPro()).isEqualTo(DEFAULT_PRO);
    }

    @Test
    @Transactional
    public void createPricingContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pricingContentRepository.findAll().size();

        // Create the PricingContent with an existing ID
        pricingContent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPricingContentMockMvc.perform(post("/api/pricing-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pricingContent)))
            .andExpect(status().isBadRequest());

        // Validate the PricingContent in the database
        List<PricingContent> pricingContentList = pricingContentRepository.findAll();
        assertThat(pricingContentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricingContentRepository.findAll().size();
        // set the field null
        pricingContent.setContent(null);

        // Create the PricingContent, which fails.


        restPricingContentMockMvc.perform(post("/api/pricing-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pricingContent)))
            .andExpect(status().isBadRequest());

        List<PricingContent> pricingContentList = pricingContentRepository.findAll();
        assertThat(pricingContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricingContentRepository.findAll().size();
        // set the field null
        pricingContent.setPro(null);

        // Create the PricingContent, which fails.


        restPricingContentMockMvc.perform(post("/api/pricing-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pricingContent)))
            .andExpect(status().isBadRequest());

        List<PricingContent> pricingContentList = pricingContentRepository.findAll();
        assertThat(pricingContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPricingContents() throws Exception {
        // Initialize the database
        pricingContentRepository.saveAndFlush(pricingContent);

        // Get all the pricingContentList
        restPricingContentMockMvc.perform(get("/api/pricing-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pricingContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].pro").value(hasItem(DEFAULT_PRO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPricingContent() throws Exception {
        // Initialize the database
        pricingContentRepository.saveAndFlush(pricingContent);

        // Get the pricingContent
        restPricingContentMockMvc.perform(get("/api/pricing-contents/{id}", pricingContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pricingContent.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.pro").value(DEFAULT_PRO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPricingContent() throws Exception {
        // Get the pricingContent
        restPricingContentMockMvc.perform(get("/api/pricing-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePricingContent() throws Exception {
        // Initialize the database
        pricingContentService.save(pricingContent);

        int databaseSizeBeforeUpdate = pricingContentRepository.findAll().size();

        // Update the pricingContent
        PricingContent updatedPricingContent = pricingContentRepository.findById(pricingContent.getId()).get();
        // Disconnect from session so that the updates on updatedPricingContent are not directly saved in db
        em.detach(updatedPricingContent);
        updatedPricingContent
            .content(UPDATED_CONTENT)
            .pro(UPDATED_PRO);

        restPricingContentMockMvc.perform(put("/api/pricing-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPricingContent)))
            .andExpect(status().isOk());

        // Validate the PricingContent in the database
        List<PricingContent> pricingContentList = pricingContentRepository.findAll();
        assertThat(pricingContentList).hasSize(databaseSizeBeforeUpdate);
        PricingContent testPricingContent = pricingContentList.get(pricingContentList.size() - 1);
        assertThat(testPricingContent.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPricingContent.isPro()).isEqualTo(UPDATED_PRO);
    }

    @Test
    @Transactional
    public void updateNonExistingPricingContent() throws Exception {
        int databaseSizeBeforeUpdate = pricingContentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPricingContentMockMvc.perform(put("/api/pricing-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pricingContent)))
            .andExpect(status().isBadRequest());

        // Validate the PricingContent in the database
        List<PricingContent> pricingContentList = pricingContentRepository.findAll();
        assertThat(pricingContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePricingContent() throws Exception {
        // Initialize the database
        pricingContentService.save(pricingContent);

        int databaseSizeBeforeDelete = pricingContentRepository.findAll().size();

        // Delete the pricingContent
        restPricingContentMockMvc.perform(delete("/api/pricing-contents/{id}", pricingContent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PricingContent> pricingContentList = pricingContentRepository.findAll();
        assertThat(pricingContentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
