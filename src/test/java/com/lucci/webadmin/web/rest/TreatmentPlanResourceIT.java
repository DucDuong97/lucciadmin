package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.TreatmentPlan;
import com.lucci.webadmin.domain.Customer;
import com.lucci.webadmin.domain.PricingCard;
import com.lucci.webadmin.repository.TreatmentPlanRepository;
import com.lucci.webadmin.service.TreatmentPlanService;
import com.lucci.webadmin.service.dto.TreatmentPlanDTO;
import com.lucci.webadmin.service.mapper.TreatmentPlanMapper;

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
 * Integration tests for the {@link TreatmentPlanResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TreatmentPlanResourceIT {

    private static final String DEFAULT_PRESENT_COMPLAINT = "AAAAAAAAAA";
    private static final String UPDATED_PRESENT_COMPLAINT = "BBBBBBBBBB";

    private static final String DEFAULT_PAST_MEDICAL_HISTORY = "AAAAAAAAAA";
    private static final String UPDATED_PAST_MEDICAL_HISTORY = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private TreatmentPlanRepository treatmentPlanRepository;

    @Autowired
    private TreatmentPlanMapper treatmentPlanMapper;

    @Autowired
    private TreatmentPlanService treatmentPlanService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTreatmentPlanMockMvc;

    private TreatmentPlan treatmentPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TreatmentPlan createEntity(EntityManager em) {
        TreatmentPlan treatmentPlan = new TreatmentPlan()
            .presentComplaint(DEFAULT_PRESENT_COMPLAINT)
            .pastMedicalHistory(DEFAULT_PAST_MEDICAL_HISTORY)
            .note(DEFAULT_NOTE);
        // Add required entity
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            customer = CustomerResourceIT.createEntity(em);
            em.persist(customer);
            em.flush();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        treatmentPlan.setCustomer(customer);
        // Add required entity
        PricingCard pricingCard;
        if (TestUtil.findAll(em, PricingCard.class).isEmpty()) {
            pricingCard = PricingCardResourceIT.createEntity(em);
            em.persist(pricingCard);
            em.flush();
        } else {
            pricingCard = TestUtil.findAll(em, PricingCard.class).get(0);
        }
        treatmentPlan.setService(pricingCard);
        return treatmentPlan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TreatmentPlan createUpdatedEntity(EntityManager em) {
        TreatmentPlan treatmentPlan = new TreatmentPlan()
            .presentComplaint(UPDATED_PRESENT_COMPLAINT)
            .pastMedicalHistory(UPDATED_PAST_MEDICAL_HISTORY)
            .note(UPDATED_NOTE);
        // Add required entity
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            customer = CustomerResourceIT.createUpdatedEntity(em);
            em.persist(customer);
            em.flush();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        treatmentPlan.setCustomer(customer);
        // Add required entity
        PricingCard pricingCard;
        if (TestUtil.findAll(em, PricingCard.class).isEmpty()) {
            pricingCard = PricingCardResourceIT.createUpdatedEntity(em);
            em.persist(pricingCard);
            em.flush();
        } else {
            pricingCard = TestUtil.findAll(em, PricingCard.class).get(0);
        }
        treatmentPlan.setService(pricingCard);
        return treatmentPlan;
    }

    @BeforeEach
    public void initTest() {
        treatmentPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreatmentPlan() throws Exception {
        int databaseSizeBeforeCreate = treatmentPlanRepository.findAll().size();
        // Create the TreatmentPlan
        TreatmentPlanDTO treatmentPlanDTO = treatmentPlanMapper.toDto(treatmentPlan);
        restTreatmentPlanMockMvc.perform(post("/api/treatment-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the TreatmentPlan in the database
        List<TreatmentPlan> treatmentPlanList = treatmentPlanRepository.findAll();
        assertThat(treatmentPlanList).hasSize(databaseSizeBeforeCreate + 1);
        TreatmentPlan testTreatmentPlan = treatmentPlanList.get(treatmentPlanList.size() - 1);
        assertThat(testTreatmentPlan.getPresentComplaint()).isEqualTo(DEFAULT_PRESENT_COMPLAINT);
        assertThat(testTreatmentPlan.getPastMedicalHistory()).isEqualTo(DEFAULT_PAST_MEDICAL_HISTORY);
        assertThat(testTreatmentPlan.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createTreatmentPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatmentPlanRepository.findAll().size();

        // Create the TreatmentPlan with an existing ID
        treatmentPlan.setId(1L);
        TreatmentPlanDTO treatmentPlanDTO = treatmentPlanMapper.toDto(treatmentPlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatmentPlanMockMvc.perform(post("/api/treatment-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentPlan in the database
        List<TreatmentPlan> treatmentPlanList = treatmentPlanRepository.findAll();
        assertThat(treatmentPlanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTreatmentPlans() throws Exception {
        // Initialize the database
        treatmentPlanRepository.saveAndFlush(treatmentPlan);

        // Get all the treatmentPlanList
        restTreatmentPlanMockMvc.perform(get("/api/treatment-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatmentPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].presentComplaint").value(hasItem(DEFAULT_PRESENT_COMPLAINT)))
            .andExpect(jsonPath("$.[*].pastMedicalHistory").value(hasItem(DEFAULT_PAST_MEDICAL_HISTORY)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    @Transactional
    public void getTreatmentPlan() throws Exception {
        // Initialize the database
        treatmentPlanRepository.saveAndFlush(treatmentPlan);

        // Get the treatmentPlan
        restTreatmentPlanMockMvc.perform(get("/api/treatment-plans/{id}", treatmentPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(treatmentPlan.getId().intValue()))
            .andExpect(jsonPath("$.presentComplaint").value(DEFAULT_PRESENT_COMPLAINT))
            .andExpect(jsonPath("$.pastMedicalHistory").value(DEFAULT_PAST_MEDICAL_HISTORY))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingTreatmentPlan() throws Exception {
        // Get the treatmentPlan
        restTreatmentPlanMockMvc.perform(get("/api/treatment-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatmentPlan() throws Exception {
        // Initialize the database
        treatmentPlanRepository.saveAndFlush(treatmentPlan);

        int databaseSizeBeforeUpdate = treatmentPlanRepository.findAll().size();

        // Update the treatmentPlan
        TreatmentPlan updatedTreatmentPlan = treatmentPlanRepository.findById(treatmentPlan.getId()).get();
        // Disconnect from session so that the updates on updatedTreatmentPlan are not directly saved in db
        em.detach(updatedTreatmentPlan);
        updatedTreatmentPlan
            .presentComplaint(UPDATED_PRESENT_COMPLAINT)
            .pastMedicalHistory(UPDATED_PAST_MEDICAL_HISTORY)
            .note(UPDATED_NOTE);
        TreatmentPlanDTO treatmentPlanDTO = treatmentPlanMapper.toDto(updatedTreatmentPlan);

        restTreatmentPlanMockMvc.perform(put("/api/treatment-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentPlanDTO)))
            .andExpect(status().isOk());

        // Validate the TreatmentPlan in the database
        List<TreatmentPlan> treatmentPlanList = treatmentPlanRepository.findAll();
        assertThat(treatmentPlanList).hasSize(databaseSizeBeforeUpdate);
        TreatmentPlan testTreatmentPlan = treatmentPlanList.get(treatmentPlanList.size() - 1);
        assertThat(testTreatmentPlan.getPresentComplaint()).isEqualTo(UPDATED_PRESENT_COMPLAINT);
        assertThat(testTreatmentPlan.getPastMedicalHistory()).isEqualTo(UPDATED_PAST_MEDICAL_HISTORY);
        assertThat(testTreatmentPlan.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingTreatmentPlan() throws Exception {
        int databaseSizeBeforeUpdate = treatmentPlanRepository.findAll().size();

        // Create the TreatmentPlan
        TreatmentPlanDTO treatmentPlanDTO = treatmentPlanMapper.toDto(treatmentPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreatmentPlanMockMvc.perform(put("/api/treatment-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentPlan in the database
        List<TreatmentPlan> treatmentPlanList = treatmentPlanRepository.findAll();
        assertThat(treatmentPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTreatmentPlan() throws Exception {
        // Initialize the database
        treatmentPlanRepository.saveAndFlush(treatmentPlan);

        int databaseSizeBeforeDelete = treatmentPlanRepository.findAll().size();

        // Delete the treatmentPlan
        restTreatmentPlanMockMvc.perform(delete("/api/treatment-plans/{id}", treatmentPlan.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TreatmentPlan> treatmentPlanList = treatmentPlanRepository.findAll();
        assertThat(treatmentPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
