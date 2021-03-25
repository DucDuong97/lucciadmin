package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.Nurse;
import com.lucci.webadmin.repository.NurseRepository;
import com.lucci.webadmin.service.NurseService;

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
 * Integration tests for the {@link NurseResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NurseResourceIT {

    private static final Integer DEFAULT_SALARY = 1;
    private static final Integer UPDATED_SALARY = 2;

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNurseMockMvc;

    private Nurse nurse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nurse createEntity(EntityManager em) {
        Nurse nurse = new Nurse()
            .salary(DEFAULT_SALARY);
        return nurse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nurse createUpdatedEntity(EntityManager em) {
        Nurse nurse = new Nurse()
            .salary(UPDATED_SALARY);
        return nurse;
    }

    @BeforeEach
    public void initTest() {
        nurse = createEntity(em);
    }

    @Test
    @Transactional
    public void createNurse() throws Exception {
        int databaseSizeBeforeCreate = nurseRepository.findAll().size();
        // Create the Nurse
        restNurseMockMvc.perform(post("/api/nurses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nurse)))
            .andExpect(status().isCreated());

        // Validate the Nurse in the database
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeCreate + 1);
        Nurse testNurse = nurseList.get(nurseList.size() - 1);
        assertThat(testNurse.getSalary()).isEqualTo(DEFAULT_SALARY);
    }

    @Test
    @Transactional
    public void createNurseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nurseRepository.findAll().size();

        // Create the Nurse with an existing ID
        nurse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNurseMockMvc.perform(post("/api/nurses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nurse)))
            .andExpect(status().isBadRequest());

        // Validate the Nurse in the database
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNurses() throws Exception {
        // Initialize the database
        nurseRepository.saveAndFlush(nurse);

        // Get all the nurseList
        restNurseMockMvc.perform(get("/api/nurses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nurse.getId().intValue())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY)));
    }
    
    @Test
    @Transactional
    public void getNurse() throws Exception {
        // Initialize the database
        nurseRepository.saveAndFlush(nurse);

        // Get the nurse
        restNurseMockMvc.perform(get("/api/nurses/{id}", nurse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nurse.getId().intValue()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY));
    }
    @Test
    @Transactional
    public void getNonExistingNurse() throws Exception {
        // Get the nurse
        restNurseMockMvc.perform(get("/api/nurses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNurse() throws Exception {
        // Initialize the database
        nurseService.save(nurse);

        int databaseSizeBeforeUpdate = nurseRepository.findAll().size();

        // Update the nurse
        Nurse updatedNurse = nurseRepository.findById(nurse.getId()).get();
        // Disconnect from session so that the updates on updatedNurse are not directly saved in db
        em.detach(updatedNurse);
        updatedNurse
            .salary(UPDATED_SALARY);

        restNurseMockMvc.perform(put("/api/nurses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNurse)))
            .andExpect(status().isOk());

        // Validate the Nurse in the database
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeUpdate);
        Nurse testNurse = nurseList.get(nurseList.size() - 1);
        assertThat(testNurse.getSalary()).isEqualTo(UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void updateNonExistingNurse() throws Exception {
        int databaseSizeBeforeUpdate = nurseRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNurseMockMvc.perform(put("/api/nurses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nurse)))
            .andExpect(status().isBadRequest());

        // Validate the Nurse in the database
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNurse() throws Exception {
        // Initialize the database
        nurseService.save(nurse);

        int databaseSizeBeforeDelete = nurseRepository.findAll().size();

        // Delete the nurse
        restNurseMockMvc.perform(delete("/api/nurses/{id}", nurse.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
