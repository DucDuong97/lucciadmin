package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.Potential;
import com.lucci.webadmin.domain.PricingCard;
import com.lucci.webadmin.domain.Branch;
import com.lucci.webadmin.repository.PotentialRepository;
import com.lucci.webadmin.service.PotentialService;
import com.lucci.webadmin.service.dto.PotentialDTO;
import com.lucci.webadmin.service.mapper.PotentialMapper;

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

import com.lucci.webadmin.domain.enumeration.Gender;
/**
 * Integration tests for the {@link PotentialResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PotentialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONE = 1L;
    private static final Long UPDATED_PHONE = 2L;

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    @Autowired
    private PotentialRepository potentialRepository;

    @Autowired
    private PotentialMapper potentialMapper;

    @Autowired
    private PotentialService potentialService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPotentialMockMvc;

    private Potential potential;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Potential createEntity(EntityManager em) {
        Potential potential = new Potential()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .gender(DEFAULT_GENDER);
        // Add required entity
        PricingCard pricingCard;
        if (TestUtil.findAll(em, PricingCard.class).isEmpty()) {
            pricingCard = PricingCardResourceIT.createEntity(em);
            em.persist(pricingCard);
            em.flush();
        } else {
            pricingCard = TestUtil.findAll(em, PricingCard.class).get(0);
        }
        potential.setService(pricingCard);
        // Add required entity
        Branch branch;
        if (TestUtil.findAll(em, Branch.class).isEmpty()) {
            branch = BranchResourceIT.createEntity(em);
            em.persist(branch);
            em.flush();
        } else {
            branch = TestUtil.findAll(em, Branch.class).get(0);
        }
        potential.setBranch(branch);
        return potential;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Potential createUpdatedEntity(EntityManager em) {
        Potential potential = new Potential()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .gender(UPDATED_GENDER);
        // Add required entity
        PricingCard pricingCard;
        if (TestUtil.findAll(em, PricingCard.class).isEmpty()) {
            pricingCard = PricingCardResourceIT.createUpdatedEntity(em);
            em.persist(pricingCard);
            em.flush();
        } else {
            pricingCard = TestUtil.findAll(em, PricingCard.class).get(0);
        }
        potential.setService(pricingCard);
        // Add required entity
        Branch branch;
        if (TestUtil.findAll(em, Branch.class).isEmpty()) {
            branch = BranchResourceIT.createUpdatedEntity(em);
            em.persist(branch);
            em.flush();
        } else {
            branch = TestUtil.findAll(em, Branch.class).get(0);
        }
        potential.setBranch(branch);
        return potential;
    }

    @BeforeEach
    public void initTest() {
        potential = createEntity(em);
    }

    @Test
    @Transactional
    public void createPotential() throws Exception {
        int databaseSizeBeforeCreate = potentialRepository.findAll().size();
        // Create the Potential
        PotentialDTO potentialDTO = potentialMapper.toDto(potential);
        restPotentialMockMvc.perform(post("/api/potentials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potentialDTO)))
            .andExpect(status().isCreated());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeCreate + 1);
        Potential testPotential = potentialList.get(potentialList.size() - 1);
        assertThat(testPotential.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPotential.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPotential.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    public void createPotentialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = potentialRepository.findAll().size();

        // Create the Potential with an existing ID
        potential.setId(1L);
        PotentialDTO potentialDTO = potentialMapper.toDto(potential);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPotentialMockMvc.perform(post("/api/potentials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potentialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = potentialRepository.findAll().size();
        // set the field null
        potential.setGender(null);

        // Create the Potential, which fails.
        PotentialDTO potentialDTO = potentialMapper.toDto(potential);


        restPotentialMockMvc.perform(post("/api/potentials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potentialDTO)))
            .andExpect(status().isBadRequest());

        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPotentials() throws Exception {
        // Initialize the database
        potentialRepository.saveAndFlush(potential);

        // Get all the potentialList
        restPotentialMockMvc.perform(get("/api/potentials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(potential.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));
    }
    
    @Test
    @Transactional
    public void getPotential() throws Exception {
        // Initialize the database
        potentialRepository.saveAndFlush(potential);

        // Get the potential
        restPotentialMockMvc.perform(get("/api/potentials/{id}", potential.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(potential.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPotential() throws Exception {
        // Get the potential
        restPotentialMockMvc.perform(get("/api/potentials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePotential() throws Exception {
        // Initialize the database
        potentialRepository.saveAndFlush(potential);

        int databaseSizeBeforeUpdate = potentialRepository.findAll().size();

        // Update the potential
        Potential updatedPotential = potentialRepository.findById(potential.getId()).get();
        // Disconnect from session so that the updates on updatedPotential are not directly saved in db
        em.detach(updatedPotential);
        updatedPotential
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .gender(UPDATED_GENDER);
        PotentialDTO potentialDTO = potentialMapper.toDto(updatedPotential);

        restPotentialMockMvc.perform(put("/api/potentials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potentialDTO)))
            .andExpect(status().isOk());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeUpdate);
        Potential testPotential = potentialList.get(potentialList.size() - 1);
        assertThat(testPotential.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPotential.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPotential.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void updateNonExistingPotential() throws Exception {
        int databaseSizeBeforeUpdate = potentialRepository.findAll().size();

        // Create the Potential
        PotentialDTO potentialDTO = potentialMapper.toDto(potential);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPotentialMockMvc.perform(put("/api/potentials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potentialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Potential in the database
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePotential() throws Exception {
        // Initialize the database
        potentialRepository.saveAndFlush(potential);

        int databaseSizeBeforeDelete = potentialRepository.findAll().size();

        // Delete the potential
        restPotentialMockMvc.perform(delete("/api/potentials/{id}", potential.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Potential> potentialList = potentialRepository.findAll();
        assertThat(potentialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
