package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.SingletonContent;
import com.lucci.webadmin.repository.SingletonContentRepository;
import com.lucci.webadmin.service.SingletonContentService;

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

import com.lucci.webadmin.domain.enumeration.ContentType;
/**
 * Integration tests for the {@link SingletonContentResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SingletonContentResourceIT {

    private static final ContentType DEFAULT_TYPE = ContentType.PHONE;
    private static final ContentType UPDATED_TYPE = ContentType.EMAIL;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private SingletonContentRepository singletonContentRepository;

    @Autowired
    private SingletonContentService singletonContentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSingletonContentMockMvc;

    private SingletonContent singletonContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SingletonContent createEntity(EntityManager em) {
        SingletonContent singletonContent = new SingletonContent()
            .type(DEFAULT_TYPE)
            .content(DEFAULT_CONTENT);
        return singletonContent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SingletonContent createUpdatedEntity(EntityManager em) {
        SingletonContent singletonContent = new SingletonContent()
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT);
        return singletonContent;
    }

    @BeforeEach
    public void initTest() {
        singletonContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createSingletonContent() throws Exception {
        int databaseSizeBeforeCreate = singletonContentRepository.findAll().size();
        // Create the SingletonContent
        restSingletonContentMockMvc.perform(post("/api/singleton-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singletonContent)))
            .andExpect(status().isCreated());

        // Validate the SingletonContent in the database
        List<SingletonContent> singletonContentList = singletonContentRepository.findAll();
        assertThat(singletonContentList).hasSize(databaseSizeBeforeCreate + 1);
        SingletonContent testSingletonContent = singletonContentList.get(singletonContentList.size() - 1);
        assertThat(testSingletonContent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSingletonContent.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createSingletonContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = singletonContentRepository.findAll().size();

        // Create the SingletonContent with an existing ID
        singletonContent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSingletonContentMockMvc.perform(post("/api/singleton-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singletonContent)))
            .andExpect(status().isBadRequest());

        // Validate the SingletonContent in the database
        List<SingletonContent> singletonContentList = singletonContentRepository.findAll();
        assertThat(singletonContentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = singletonContentRepository.findAll().size();
        // set the field null
        singletonContent.setType(null);

        // Create the SingletonContent, which fails.


        restSingletonContentMockMvc.perform(post("/api/singleton-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singletonContent)))
            .andExpect(status().isBadRequest());

        List<SingletonContent> singletonContentList = singletonContentRepository.findAll();
        assertThat(singletonContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSingletonContents() throws Exception {
        // Initialize the database
        singletonContentRepository.saveAndFlush(singletonContent);

        // Get all the singletonContentList
        restSingletonContentMockMvc.perform(get("/api/singleton-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(singletonContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
    }
    
    @Test
    @Transactional
    public void getSingletonContent() throws Exception {
        // Initialize the database
        singletonContentRepository.saveAndFlush(singletonContent);

        // Get the singletonContent
        restSingletonContentMockMvc.perform(get("/api/singleton-contents/{id}", singletonContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(singletonContent.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
    }
    @Test
    @Transactional
    public void getNonExistingSingletonContent() throws Exception {
        // Get the singletonContent
        restSingletonContentMockMvc.perform(get("/api/singleton-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSingletonContent() throws Exception {
        // Initialize the database
        singletonContentService.save(singletonContent);

        int databaseSizeBeforeUpdate = singletonContentRepository.findAll().size();

        // Update the singletonContent
        SingletonContent updatedSingletonContent = singletonContentRepository.findById(singletonContent.getId()).get();
        // Disconnect from session so that the updates on updatedSingletonContent are not directly saved in db
        em.detach(updatedSingletonContent);
        updatedSingletonContent
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT);

        restSingletonContentMockMvc.perform(put("/api/singleton-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSingletonContent)))
            .andExpect(status().isOk());

        // Validate the SingletonContent in the database
        List<SingletonContent> singletonContentList = singletonContentRepository.findAll();
        assertThat(singletonContentList).hasSize(databaseSizeBeforeUpdate);
        SingletonContent testSingletonContent = singletonContentList.get(singletonContentList.size() - 1);
        assertThat(testSingletonContent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSingletonContent.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingSingletonContent() throws Exception {
        int databaseSizeBeforeUpdate = singletonContentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSingletonContentMockMvc.perform(put("/api/singleton-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(singletonContent)))
            .andExpect(status().isBadRequest());

        // Validate the SingletonContent in the database
        List<SingletonContent> singletonContentList = singletonContentRepository.findAll();
        assertThat(singletonContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSingletonContent() throws Exception {
        // Initialize the database
        singletonContentService.save(singletonContent);

        int databaseSizeBeforeDelete = singletonContentRepository.findAll().size();

        // Delete the singletonContent
        restSingletonContentMockMvc.perform(delete("/api/singleton-contents/{id}", singletonContent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SingletonContent> singletonContentList = singletonContentRepository.findAll();
        assertThat(singletonContentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
