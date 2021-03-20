package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.ServiceOption;
import com.lucci.webadmin.repository.ServiceOptionRepository;
import com.lucci.webadmin.service.ServiceOptionService;

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
 * Integration tests for the {@link ServiceOptionResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceOptionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFITS = "AAAAAAAAAA";
    private static final String UPDATED_BENEFITS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    @Autowired
    private ServiceOptionRepository serviceOptionRepository;

    @Autowired
    private ServiceOptionService serviceOptionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceOptionMockMvc;

    private ServiceOption serviceOption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOption createEntity(EntityManager em) {
        ServiceOption serviceOption = new ServiceOption()
            .name(DEFAULT_NAME)
            .benefits(DEFAULT_BENEFITS)
            .price(DEFAULT_PRICE);
        return serviceOption;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOption createUpdatedEntity(EntityManager em) {
        ServiceOption serviceOption = new ServiceOption()
            .name(UPDATED_NAME)
            .benefits(UPDATED_BENEFITS)
            .price(UPDATED_PRICE);
        return serviceOption;
    }

    @BeforeEach
    public void initTest() {
        serviceOption = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceOption() throws Exception {
        int databaseSizeBeforeCreate = serviceOptionRepository.findAll().size();
        // Create the ServiceOption
        restServiceOptionMockMvc.perform(post("/api/service-options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOption)))
            .andExpect(status().isCreated());

        // Validate the ServiceOption in the database
        List<ServiceOption> serviceOptionList = serviceOptionRepository.findAll();
        assertThat(serviceOptionList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceOption testServiceOption = serviceOptionList.get(serviceOptionList.size() - 1);
        assertThat(testServiceOption.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceOption.getBenefits()).isEqualTo(DEFAULT_BENEFITS);
        assertThat(testServiceOption.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createServiceOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceOptionRepository.findAll().size();

        // Create the ServiceOption with an existing ID
        serviceOption.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOptionMockMvc.perform(post("/api/service-options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOption)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOption in the database
        List<ServiceOption> serviceOptionList = serviceOptionRepository.findAll();
        assertThat(serviceOptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOptionRepository.findAll().size();
        // set the field null
        serviceOption.setName(null);

        // Create the ServiceOption, which fails.


        restServiceOptionMockMvc.perform(post("/api/service-options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOption)))
            .andExpect(status().isBadRequest());

        List<ServiceOption> serviceOptionList = serviceOptionRepository.findAll();
        assertThat(serviceOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBenefitsIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOptionRepository.findAll().size();
        // set the field null
        serviceOption.setBenefits(null);

        // Create the ServiceOption, which fails.


        restServiceOptionMockMvc.perform(post("/api/service-options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOption)))
            .andExpect(status().isBadRequest());

        List<ServiceOption> serviceOptionList = serviceOptionRepository.findAll();
        assertThat(serviceOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOptionRepository.findAll().size();
        // set the field null
        serviceOption.setPrice(null);

        // Create the ServiceOption, which fails.


        restServiceOptionMockMvc.perform(post("/api/service-options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOption)))
            .andExpect(status().isBadRequest());

        List<ServiceOption> serviceOptionList = serviceOptionRepository.findAll();
        assertThat(serviceOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceOptions() throws Exception {
        // Initialize the database
        serviceOptionRepository.saveAndFlush(serviceOption);

        // Get all the serviceOptionList
        restServiceOptionMockMvc.perform(get("/api/service-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].benefits").value(hasItem(DEFAULT_BENEFITS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }
    
    @Test
    @Transactional
    public void getServiceOption() throws Exception {
        // Initialize the database
        serviceOptionRepository.saveAndFlush(serviceOption);

        // Get the serviceOption
        restServiceOptionMockMvc.perform(get("/api/service-options/{id}", serviceOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOption.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.benefits").value(DEFAULT_BENEFITS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }
    @Test
    @Transactional
    public void getNonExistingServiceOption() throws Exception {
        // Get the serviceOption
        restServiceOptionMockMvc.perform(get("/api/service-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceOption() throws Exception {
        // Initialize the database
        serviceOptionService.save(serviceOption);

        int databaseSizeBeforeUpdate = serviceOptionRepository.findAll().size();

        // Update the serviceOption
        ServiceOption updatedServiceOption = serviceOptionRepository.findById(serviceOption.getId()).get();
        // Disconnect from session so that the updates on updatedServiceOption are not directly saved in db
        em.detach(updatedServiceOption);
        updatedServiceOption
            .name(UPDATED_NAME)
            .benefits(UPDATED_BENEFITS)
            .price(UPDATED_PRICE);

        restServiceOptionMockMvc.perform(put("/api/service-options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceOption)))
            .andExpect(status().isOk());

        // Validate the ServiceOption in the database
        List<ServiceOption> serviceOptionList = serviceOptionRepository.findAll();
        assertThat(serviceOptionList).hasSize(databaseSizeBeforeUpdate);
        ServiceOption testServiceOption = serviceOptionList.get(serviceOptionList.size() - 1);
        assertThat(testServiceOption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceOption.getBenefits()).isEqualTo(UPDATED_BENEFITS);
        assertThat(testServiceOption.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceOption() throws Exception {
        int databaseSizeBeforeUpdate = serviceOptionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOptionMockMvc.perform(put("/api/service-options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceOption)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOption in the database
        List<ServiceOption> serviceOptionList = serviceOptionRepository.findAll();
        assertThat(serviceOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceOption() throws Exception {
        // Initialize the database
        serviceOptionService.save(serviceOption);

        int databaseSizeBeforeDelete = serviceOptionRepository.findAll().size();

        // Delete the serviceOption
        restServiceOptionMockMvc.perform(delete("/api/service-options/{id}", serviceOption.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceOption> serviceOptionList = serviceOptionRepository.findAll();
        assertThat(serviceOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
