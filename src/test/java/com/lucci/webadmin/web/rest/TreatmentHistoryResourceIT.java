package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.TreatmentHistory;
import com.lucci.webadmin.repository.TreatmentHistoryRepository;
import com.lucci.webadmin.service.TreatmentHistoryService;

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
 * Integration tests for the {@link TreatmentHistoryResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TreatmentHistoryResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TreatmentHistoryRepository treatmentHistoryRepository;

    @Autowired
    private TreatmentHistoryService treatmentHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTreatmentHistoryMockMvc;

    private TreatmentHistory treatmentHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TreatmentHistory createEntity(EntityManager em) {
        TreatmentHistory treatmentHistory = new TreatmentHistory()
            .date(DEFAULT_DATE);
        return treatmentHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TreatmentHistory createUpdatedEntity(EntityManager em) {
        TreatmentHistory treatmentHistory = new TreatmentHistory()
            .date(UPDATED_DATE);
        return treatmentHistory;
    }

    @BeforeEach
    public void initTest() {
        treatmentHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreatmentHistory() throws Exception {
        int databaseSizeBeforeCreate = treatmentHistoryRepository.findAll().size();
        // Create the TreatmentHistory
        restTreatmentHistoryMockMvc.perform(post("/api/treatment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentHistory)))
            .andExpect(status().isCreated());

        // Validate the TreatmentHistory in the database
        List<TreatmentHistory> treatmentHistoryList = treatmentHistoryRepository.findAll();
        assertThat(treatmentHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TreatmentHistory testTreatmentHistory = treatmentHistoryList.get(treatmentHistoryList.size() - 1);
        assertThat(testTreatmentHistory.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createTreatmentHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatmentHistoryRepository.findAll().size();

        // Create the TreatmentHistory with an existing ID
        treatmentHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatmentHistoryMockMvc.perform(post("/api/treatment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentHistory)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentHistory in the database
        List<TreatmentHistory> treatmentHistoryList = treatmentHistoryRepository.findAll();
        assertThat(treatmentHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = treatmentHistoryRepository.findAll().size();
        // set the field null
        treatmentHistory.setDate(null);

        // Create the TreatmentHistory, which fails.


        restTreatmentHistoryMockMvc.perform(post("/api/treatment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentHistory)))
            .andExpect(status().isBadRequest());

        List<TreatmentHistory> treatmentHistoryList = treatmentHistoryRepository.findAll();
        assertThat(treatmentHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTreatmentHistories() throws Exception {
        // Initialize the database
        treatmentHistoryRepository.saveAndFlush(treatmentHistory);

        // Get all the treatmentHistoryList
        restTreatmentHistoryMockMvc.perform(get("/api/treatment-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatmentHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTreatmentHistory() throws Exception {
        // Initialize the database
        treatmentHistoryRepository.saveAndFlush(treatmentHistory);

        // Get the treatmentHistory
        restTreatmentHistoryMockMvc.perform(get("/api/treatment-histories/{id}", treatmentHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(treatmentHistory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTreatmentHistory() throws Exception {
        // Get the treatmentHistory
        restTreatmentHistoryMockMvc.perform(get("/api/treatment-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatmentHistory() throws Exception {
        // Initialize the database
        treatmentHistoryService.save(treatmentHistory);

        int databaseSizeBeforeUpdate = treatmentHistoryRepository.findAll().size();

        // Update the treatmentHistory
        TreatmentHistory updatedTreatmentHistory = treatmentHistoryRepository.findById(treatmentHistory.getId()).get();
        // Disconnect from session so that the updates on updatedTreatmentHistory are not directly saved in db
        em.detach(updatedTreatmentHistory);
        updatedTreatmentHistory
            .date(UPDATED_DATE);

        restTreatmentHistoryMockMvc.perform(put("/api/treatment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTreatmentHistory)))
            .andExpect(status().isOk());

        // Validate the TreatmentHistory in the database
        List<TreatmentHistory> treatmentHistoryList = treatmentHistoryRepository.findAll();
        assertThat(treatmentHistoryList).hasSize(databaseSizeBeforeUpdate);
        TreatmentHistory testTreatmentHistory = treatmentHistoryList.get(treatmentHistoryList.size() - 1);
        assertThat(testTreatmentHistory.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTreatmentHistory() throws Exception {
        int databaseSizeBeforeUpdate = treatmentHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreatmentHistoryMockMvc.perform(put("/api/treatment-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentHistory)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentHistory in the database
        List<TreatmentHistory> treatmentHistoryList = treatmentHistoryRepository.findAll();
        assertThat(treatmentHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTreatmentHistory() throws Exception {
        // Initialize the database
        treatmentHistoryService.save(treatmentHistory);

        int databaseSizeBeforeDelete = treatmentHistoryRepository.findAll().size();

        // Delete the treatmentHistory
        restTreatmentHistoryMockMvc.perform(delete("/api/treatment-histories/{id}", treatmentHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TreatmentHistory> treatmentHistoryList = treatmentHistoryRepository.findAll();
        assertThat(treatmentHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
