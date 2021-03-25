package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.GetMaterial;
import com.lucci.webadmin.repository.GetMaterialRepository;
import com.lucci.webadmin.service.GetMaterialService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GetMaterialResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GetMaterialResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GetMaterialRepository getMaterialRepository;

    @Autowired
    private GetMaterialService getMaterialService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGetMaterialMockMvc;

    private GetMaterial getMaterial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GetMaterial createEntity(EntityManager em) {
        GetMaterial getMaterial = new GetMaterial()
            .date(DEFAULT_DATE);
        return getMaterial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GetMaterial createUpdatedEntity(EntityManager em) {
        GetMaterial getMaterial = new GetMaterial()
            .date(UPDATED_DATE);
        return getMaterial;
    }

    @BeforeEach
    public void initTest() {
        getMaterial = createEntity(em);
    }

    @Test
    @Transactional
    public void createGetMaterial() throws Exception {
        int databaseSizeBeforeCreate = getMaterialRepository.findAll().size();
        // Create the GetMaterial
        restGetMaterialMockMvc.perform(post("/api/get-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(getMaterial)))
            .andExpect(status().isCreated());

        // Validate the GetMaterial in the database
        List<GetMaterial> getMaterialList = getMaterialRepository.findAll();
        assertThat(getMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        GetMaterial testGetMaterial = getMaterialList.get(getMaterialList.size() - 1);
        assertThat(testGetMaterial.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createGetMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = getMaterialRepository.findAll().size();

        // Create the GetMaterial with an existing ID
        getMaterial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGetMaterialMockMvc.perform(post("/api/get-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(getMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the GetMaterial in the database
        List<GetMaterial> getMaterialList = getMaterialRepository.findAll();
        assertThat(getMaterialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = getMaterialRepository.findAll().size();
        // set the field null
        getMaterial.setDate(null);

        // Create the GetMaterial, which fails.


        restGetMaterialMockMvc.perform(post("/api/get-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(getMaterial)))
            .andExpect(status().isBadRequest());

        List<GetMaterial> getMaterialList = getMaterialRepository.findAll();
        assertThat(getMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGetMaterials() throws Exception {
        // Initialize the database
        getMaterialRepository.saveAndFlush(getMaterial);

        // Get all the getMaterialList
        restGetMaterialMockMvc.perform(get("/api/get-materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(getMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getGetMaterial() throws Exception {
        // Initialize the database
        getMaterialRepository.saveAndFlush(getMaterial);

        // Get the getMaterial
        restGetMaterialMockMvc.perform(get("/api/get-materials/{id}", getMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(getMaterial.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGetMaterial() throws Exception {
        // Get the getMaterial
        restGetMaterialMockMvc.perform(get("/api/get-materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGetMaterial() throws Exception {
        // Initialize the database
        getMaterialService.save(getMaterial);

        int databaseSizeBeforeUpdate = getMaterialRepository.findAll().size();

        // Update the getMaterial
        GetMaterial updatedGetMaterial = getMaterialRepository.findById(getMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedGetMaterial are not directly saved in db
        em.detach(updatedGetMaterial);
        updatedGetMaterial
            .date(UPDATED_DATE);

        restGetMaterialMockMvc.perform(put("/api/get-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGetMaterial)))
            .andExpect(status().isOk());

        // Validate the GetMaterial in the database
        List<GetMaterial> getMaterialList = getMaterialRepository.findAll();
        assertThat(getMaterialList).hasSize(databaseSizeBeforeUpdate);
        GetMaterial testGetMaterial = getMaterialList.get(getMaterialList.size() - 1);
        assertThat(testGetMaterial.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingGetMaterial() throws Exception {
        int databaseSizeBeforeUpdate = getMaterialRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGetMaterialMockMvc.perform(put("/api/get-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(getMaterial)))
            .andExpect(status().isBadRequest());

        // Validate the GetMaterial in the database
        List<GetMaterial> getMaterialList = getMaterialRepository.findAll();
        assertThat(getMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGetMaterial() throws Exception {
        // Initialize the database
        getMaterialService.save(getMaterial);

        int databaseSizeBeforeDelete = getMaterialRepository.findAll().size();

        // Delete the getMaterial
        restGetMaterialMockMvc.perform(delete("/api/get-materials/{id}", getMaterial.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GetMaterial> getMaterialList = getMaterialRepository.findAll();
        assertThat(getMaterialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
