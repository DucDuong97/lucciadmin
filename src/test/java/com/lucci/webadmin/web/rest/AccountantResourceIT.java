package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.Accountant;
import com.lucci.webadmin.repository.AccountantRepository;
import com.lucci.webadmin.service.AccountantService;

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
 * Integration tests for the {@link AccountantResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccountantResourceIT {

    private static final Integer DEFAULT_SALARY = 1;
    private static final Integer UPDATED_SALARY = 2;

    @Autowired
    private AccountantRepository accountantRepository;

    @Autowired
    private AccountantService accountantService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountantMockMvc;

    private Accountant accountant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accountant createEntity(EntityManager em) {
        Accountant accountant = new Accountant()
            .salary(DEFAULT_SALARY);
        return accountant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accountant createUpdatedEntity(EntityManager em) {
        Accountant accountant = new Accountant()
            .salary(UPDATED_SALARY);
        return accountant;
    }

    @BeforeEach
    public void initTest() {
        accountant = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountant() throws Exception {
        int databaseSizeBeforeCreate = accountantRepository.findAll().size();
        // Create the Accountant
        restAccountantMockMvc.perform(post("/api/accountants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountant)))
            .andExpect(status().isCreated());

        // Validate the Accountant in the database
        List<Accountant> accountantList = accountantRepository.findAll();
        assertThat(accountantList).hasSize(databaseSizeBeforeCreate + 1);
        Accountant testAccountant = accountantList.get(accountantList.size() - 1);
        assertThat(testAccountant.getSalary()).isEqualTo(DEFAULT_SALARY);
    }

    @Test
    @Transactional
    public void createAccountantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountantRepository.findAll().size();

        // Create the Accountant with an existing ID
        accountant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountantMockMvc.perform(post("/api/accountants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountant)))
            .andExpect(status().isBadRequest());

        // Validate the Accountant in the database
        List<Accountant> accountantList = accountantRepository.findAll();
        assertThat(accountantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAccountants() throws Exception {
        // Initialize the database
        accountantRepository.saveAndFlush(accountant);

        // Get all the accountantList
        restAccountantMockMvc.perform(get("/api/accountants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountant.getId().intValue())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY)));
    }
    
    @Test
    @Transactional
    public void getAccountant() throws Exception {
        // Initialize the database
        accountantRepository.saveAndFlush(accountant);

        // Get the accountant
        restAccountantMockMvc.perform(get("/api/accountants/{id}", accountant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountant.getId().intValue()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY));
    }
    @Test
    @Transactional
    public void getNonExistingAccountant() throws Exception {
        // Get the accountant
        restAccountantMockMvc.perform(get("/api/accountants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountant() throws Exception {
        // Initialize the database
        accountantService.save(accountant);

        int databaseSizeBeforeUpdate = accountantRepository.findAll().size();

        // Update the accountant
        Accountant updatedAccountant = accountantRepository.findById(accountant.getId()).get();
        // Disconnect from session so that the updates on updatedAccountant are not directly saved in db
        em.detach(updatedAccountant);
        updatedAccountant
            .salary(UPDATED_SALARY);

        restAccountantMockMvc.perform(put("/api/accountants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccountant)))
            .andExpect(status().isOk());

        // Validate the Accountant in the database
        List<Accountant> accountantList = accountantRepository.findAll();
        assertThat(accountantList).hasSize(databaseSizeBeforeUpdate);
        Accountant testAccountant = accountantList.get(accountantList.size() - 1);
        assertThat(testAccountant.getSalary()).isEqualTo(UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountant() throws Exception {
        int databaseSizeBeforeUpdate = accountantRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountantMockMvc.perform(put("/api/accountants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountant)))
            .andExpect(status().isBadRequest());

        // Validate the Accountant in the database
        List<Accountant> accountantList = accountantRepository.findAll();
        assertThat(accountantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountant() throws Exception {
        // Initialize the database
        accountantService.save(accountant);

        int databaseSizeBeforeDelete = accountantRepository.findAll().size();

        // Delete the accountant
        restAccountantMockMvc.perform(delete("/api/accountants/{id}", accountant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accountant> accountantList = accountantRepository.findAll();
        assertThat(accountantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
