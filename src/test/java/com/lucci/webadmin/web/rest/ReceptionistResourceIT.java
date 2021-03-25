package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.Receptionist;
import com.lucci.webadmin.repository.ReceptionistRepository;
import com.lucci.webadmin.service.ReceptionistService;

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
 * Integration tests for the {@link ReceptionistResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReceptionistResourceIT {

    private static final Integer DEFAULT_SALARY = 1;
    private static final Integer UPDATED_SALARY = 2;

    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private ReceptionistService receptionistService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReceptionistMockMvc;

    private Receptionist receptionist;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receptionist createEntity(EntityManager em) {
        Receptionist receptionist = new Receptionist()
            .salary(DEFAULT_SALARY);
        return receptionist;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receptionist createUpdatedEntity(EntityManager em) {
        Receptionist receptionist = new Receptionist()
            .salary(UPDATED_SALARY);
        return receptionist;
    }

    @BeforeEach
    public void initTest() {
        receptionist = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceptionist() throws Exception {
        int databaseSizeBeforeCreate = receptionistRepository.findAll().size();
        // Create the Receptionist
        restReceptionistMockMvc.perform(post("/api/receptionists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receptionist)))
            .andExpect(status().isCreated());

        // Validate the Receptionist in the database
        List<Receptionist> receptionistList = receptionistRepository.findAll();
        assertThat(receptionistList).hasSize(databaseSizeBeforeCreate + 1);
        Receptionist testReceptionist = receptionistList.get(receptionistList.size() - 1);
        assertThat(testReceptionist.getSalary()).isEqualTo(DEFAULT_SALARY);
    }

    @Test
    @Transactional
    public void createReceptionistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receptionistRepository.findAll().size();

        // Create the Receptionist with an existing ID
        receptionist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceptionistMockMvc.perform(post("/api/receptionists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receptionist)))
            .andExpect(status().isBadRequest());

        // Validate the Receptionist in the database
        List<Receptionist> receptionistList = receptionistRepository.findAll();
        assertThat(receptionistList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReceptionists() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get all the receptionistList
        restReceptionistMockMvc.perform(get("/api/receptionists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receptionist.getId().intValue())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY)));
    }
    
    @Test
    @Transactional
    public void getReceptionist() throws Exception {
        // Initialize the database
        receptionistRepository.saveAndFlush(receptionist);

        // Get the receptionist
        restReceptionistMockMvc.perform(get("/api/receptionists/{id}", receptionist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receptionist.getId().intValue()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY));
    }
    @Test
    @Transactional
    public void getNonExistingReceptionist() throws Exception {
        // Get the receptionist
        restReceptionistMockMvc.perform(get("/api/receptionists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceptionist() throws Exception {
        // Initialize the database
        receptionistService.save(receptionist);

        int databaseSizeBeforeUpdate = receptionistRepository.findAll().size();

        // Update the receptionist
        Receptionist updatedReceptionist = receptionistRepository.findById(receptionist.getId()).get();
        // Disconnect from session so that the updates on updatedReceptionist are not directly saved in db
        em.detach(updatedReceptionist);
        updatedReceptionist
            .salary(UPDATED_SALARY);

        restReceptionistMockMvc.perform(put("/api/receptionists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReceptionist)))
            .andExpect(status().isOk());

        // Validate the Receptionist in the database
        List<Receptionist> receptionistList = receptionistRepository.findAll();
        assertThat(receptionistList).hasSize(databaseSizeBeforeUpdate);
        Receptionist testReceptionist = receptionistList.get(receptionistList.size() - 1);
        assertThat(testReceptionist.getSalary()).isEqualTo(UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void updateNonExistingReceptionist() throws Exception {
        int databaseSizeBeforeUpdate = receptionistRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceptionistMockMvc.perform(put("/api/receptionists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receptionist)))
            .andExpect(status().isBadRequest());

        // Validate the Receptionist in the database
        List<Receptionist> receptionistList = receptionistRepository.findAll();
        assertThat(receptionistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReceptionist() throws Exception {
        // Initialize the database
        receptionistService.save(receptionist);

        int databaseSizeBeforeDelete = receptionistRepository.findAll().size();

        // Delete the receptionist
        restReceptionistMockMvc.perform(delete("/api/receptionists/{id}", receptionist.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Receptionist> receptionistList = receptionistRepository.findAll();
        assertThat(receptionistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
