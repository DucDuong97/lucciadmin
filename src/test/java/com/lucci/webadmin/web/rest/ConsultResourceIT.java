package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.Consult;
import com.lucci.webadmin.domain.Customer;
import com.lucci.webadmin.repository.ConsultRepository;
import com.lucci.webadmin.service.ConsultService;
import com.lucci.webadmin.service.dto.ConsultDTO;
import com.lucci.webadmin.service.mapper.ConsultMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.lucci.webadmin.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConsultResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConsultResourceIT {

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private ConsultRepository consultRepository;

    @Mock
    private ConsultRepository consultRepositoryMock;

    @Autowired
    private ConsultMapper consultMapper;

    @Mock
    private ConsultService consultServiceMock;

    @Autowired
    private ConsultService consultService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsultMockMvc;

    private Consult consult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consult createEntity(EntityManager em) {
        Consult consult = new Consult()
            .time(DEFAULT_TIME)
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
        consult.setCustomer(customer);
        return consult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consult createUpdatedEntity(EntityManager em) {
        Consult consult = new Consult()
            .time(UPDATED_TIME)
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
        consult.setCustomer(customer);
        return consult;
    }

    @BeforeEach
    public void initTest() {
        consult = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsult() throws Exception {
        int databaseSizeBeforeCreate = consultRepository.findAll().size();
        // Create the Consult
        ConsultDTO consultDTO = consultMapper.toDto(consult);
        restConsultMockMvc.perform(post("/api/consults")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultDTO)))
            .andExpect(status().isCreated());

        // Validate the Consult in the database
        List<Consult> consultList = consultRepository.findAll();
        assertThat(consultList).hasSize(databaseSizeBeforeCreate + 1);
        Consult testConsult = consultList.get(consultList.size() - 1);
        assertThat(testConsult.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testConsult.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createConsultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultRepository.findAll().size();

        // Create the Consult with an existing ID
        consult.setId(1L);
        ConsultDTO consultDTO = consultMapper.toDto(consult);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultMockMvc.perform(post("/api/consults")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consult in the database
        List<Consult> consultList = consultRepository.findAll();
        assertThat(consultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultRepository.findAll().size();
        // set the field null
        consult.setTime(null);

        // Create the Consult, which fails.
        ConsultDTO consultDTO = consultMapper.toDto(consult);


        restConsultMockMvc.perform(post("/api/consults")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultDTO)))
            .andExpect(status().isBadRequest());

        List<Consult> consultList = consultRepository.findAll();
        assertThat(consultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsults() throws Exception {
        // Initialize the database
        consultRepository.saveAndFlush(consult);

        // Get all the consultList
        restConsultMockMvc.perform(get("/api/consults?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consult.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllConsultsWithEagerRelationshipsIsEnabled() throws Exception {
        when(consultServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConsultMockMvc.perform(get("/api/consults?eagerload=true"))
            .andExpect(status().isOk());

        verify(consultServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllConsultsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(consultServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConsultMockMvc.perform(get("/api/consults?eagerload=true"))
            .andExpect(status().isOk());

        verify(consultServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getConsult() throws Exception {
        // Initialize the database
        consultRepository.saveAndFlush(consult);

        // Get the consult
        restConsultMockMvc.perform(get("/api/consults/{id}", consult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consult.getId().intValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    @Transactional
    public void getNonExistingConsult() throws Exception {
        // Get the consult
        restConsultMockMvc.perform(get("/api/consults/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsult() throws Exception {
        // Initialize the database
        consultRepository.saveAndFlush(consult);

        int databaseSizeBeforeUpdate = consultRepository.findAll().size();

        // Update the consult
        Consult updatedConsult = consultRepository.findById(consult.getId()).get();
        // Disconnect from session so that the updates on updatedConsult are not directly saved in db
        em.detach(updatedConsult);
        updatedConsult
            .time(UPDATED_TIME)
            .note(UPDATED_NOTE);
        ConsultDTO consultDTO = consultMapper.toDto(updatedConsult);

        restConsultMockMvc.perform(put("/api/consults")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultDTO)))
            .andExpect(status().isOk());

        // Validate the Consult in the database
        List<Consult> consultList = consultRepository.findAll();
        assertThat(consultList).hasSize(databaseSizeBeforeUpdate);
        Consult testConsult = consultList.get(consultList.size() - 1);
        assertThat(testConsult.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testConsult.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingConsult() throws Exception {
        int databaseSizeBeforeUpdate = consultRepository.findAll().size();

        // Create the Consult
        ConsultDTO consultDTO = consultMapper.toDto(consult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultMockMvc.perform(put("/api/consults")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consult in the database
        List<Consult> consultList = consultRepository.findAll();
        assertThat(consultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsult() throws Exception {
        // Initialize the database
        consultRepository.saveAndFlush(consult);

        int databaseSizeBeforeDelete = consultRepository.findAll().size();

        // Delete the consult
        restConsultMockMvc.perform(delete("/api/consults/{id}", consult.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consult> consultList = consultRepository.findAll();
        assertThat(consultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
