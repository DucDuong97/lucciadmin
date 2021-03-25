package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.MedicalRecord;
import com.lucci.webadmin.repository.MedicalRecordRepository;
import com.lucci.webadmin.service.MedicalRecordService;

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
 * Integration tests for the {@link MedicalRecordResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MedicalRecordResourceIT {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicalRecordMockMvc;

    private MedicalRecord medicalRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalRecord createEntity(EntityManager em) {
        MedicalRecord medicalRecord = new MedicalRecord();
        return medicalRecord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalRecord createUpdatedEntity(EntityManager em) {
        MedicalRecord medicalRecord = new MedicalRecord();
        return medicalRecord;
    }

    @BeforeEach
    public void initTest() {
        medicalRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalRecord() throws Exception {
        int databaseSizeBeforeCreate = medicalRecordRepository.findAll().size();
        // Create the MedicalRecord
        restMedicalRecordMockMvc.perform(post("/api/medical-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalRecord)))
            .andExpect(status().isCreated());

        // Validate the MedicalRecord in the database
        List<MedicalRecord> medicalRecordList = medicalRecordRepository.findAll();
        assertThat(medicalRecordList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalRecord testMedicalRecord = medicalRecordList.get(medicalRecordList.size() - 1);
    }

    @Test
    @Transactional
    public void createMedicalRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalRecordRepository.findAll().size();

        // Create the MedicalRecord with an existing ID
        medicalRecord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalRecordMockMvc.perform(post("/api/medical-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalRecord)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalRecord in the database
        List<MedicalRecord> medicalRecordList = medicalRecordRepository.findAll();
        assertThat(medicalRecordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMedicalRecords() throws Exception {
        // Initialize the database
        medicalRecordRepository.saveAndFlush(medicalRecord);

        // Get all the medicalRecordList
        restMedicalRecordMockMvc.perform(get("/api/medical-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalRecord.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMedicalRecord() throws Exception {
        // Initialize the database
        medicalRecordRepository.saveAndFlush(medicalRecord);

        // Get the medicalRecord
        restMedicalRecordMockMvc.perform(get("/api/medical-records/{id}", medicalRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicalRecord.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMedicalRecord() throws Exception {
        // Get the medicalRecord
        restMedicalRecordMockMvc.perform(get("/api/medical-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalRecord() throws Exception {
        // Initialize the database
        medicalRecordService.save(medicalRecord);

        int databaseSizeBeforeUpdate = medicalRecordRepository.findAll().size();

        // Update the medicalRecord
        MedicalRecord updatedMedicalRecord = medicalRecordRepository.findById(medicalRecord.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalRecord are not directly saved in db
        em.detach(updatedMedicalRecord);

        restMedicalRecordMockMvc.perform(put("/api/medical-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalRecord)))
            .andExpect(status().isOk());

        // Validate the MedicalRecord in the database
        List<MedicalRecord> medicalRecordList = medicalRecordRepository.findAll();
        assertThat(medicalRecordList).hasSize(databaseSizeBeforeUpdate);
        MedicalRecord testMedicalRecord = medicalRecordList.get(medicalRecordList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalRecord() throws Exception {
        int databaseSizeBeforeUpdate = medicalRecordRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalRecordMockMvc.perform(put("/api/medical-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalRecord)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalRecord in the database
        List<MedicalRecord> medicalRecordList = medicalRecordRepository.findAll();
        assertThat(medicalRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicalRecord() throws Exception {
        // Initialize the database
        medicalRecordService.save(medicalRecord);

        int databaseSizeBeforeDelete = medicalRecordRepository.findAll().size();

        // Delete the medicalRecord
        restMedicalRecordMockMvc.perform(delete("/api/medical-records/{id}", medicalRecord.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalRecord> medicalRecordList = medicalRecordRepository.findAll();
        assertThat(medicalRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
