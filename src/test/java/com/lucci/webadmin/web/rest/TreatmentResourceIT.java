package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.Treatment;
import com.lucci.webadmin.domain.Employee;
import com.lucci.webadmin.domain.TreatmentPlan;
import com.lucci.webadmin.domain.enumeration.TreatmentState;
import com.lucci.webadmin.repository.TreatmentRepository;
import com.lucci.webadmin.service.TreatmentService;
import com.lucci.webadmin.service.dto.TreatmentDTO;
import com.lucci.webadmin.service.mapper.TreatmentMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TreatmentResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TreatmentResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NEXT_PLAN = "AAAAAAAAAA";
    private static final String UPDATED_NEXT_PLAN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REVISIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVISIT_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Mock
    private TreatmentRepository treatmentRepositoryMock;

    @Autowired
    private TreatmentMapper treatmentMapper;

    @Mock
    private TreatmentService treatmentServiceMock;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTreatmentMockMvc;

    private Treatment treatment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Treatment createEntity(EntityManager em) {
        Treatment treatment = new Treatment()
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .nextPlan(DEFAULT_NEXT_PLAN)
            .revisitDate(DEFAULT_REVISIT_DATE);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        treatment.setDoctor(employee);
        // Add required entity
        TreatmentPlan treatmentPlan;
        if (TestUtil.findAll(em, TreatmentPlan.class).isEmpty()) {
            treatmentPlan = TreatmentPlanResourceIT.createEntity(em);
            em.persist(treatmentPlan);
            em.flush();
        } else {
            treatmentPlan = TestUtil.findAll(em, TreatmentPlan.class).get(0);
        }
        treatment.setTreatmentPlan(treatmentPlan);
        return treatment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Treatment createUpdatedEntity(EntityManager em) {
        Treatment treatment = new Treatment()
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .nextPlan(UPDATED_NEXT_PLAN)
            .revisitDate(UPDATED_REVISIT_DATE);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createUpdatedEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        treatment.setDoctor(employee);
        // Add required entity
        TreatmentPlan treatmentPlan;
        if (TestUtil.findAll(em, TreatmentPlan.class).isEmpty()) {
            treatmentPlan = TreatmentPlanResourceIT.createUpdatedEntity(em);
            em.persist(treatmentPlan);
            em.flush();
        } else {
            treatmentPlan = TestUtil.findAll(em, TreatmentPlan.class).get(0);
        }
        treatment.setTreatmentPlan(treatmentPlan);
        return treatment;
    }

    @BeforeEach
    public void initTest() {
        treatment = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreatment() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();
        // Create the Treatment
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);
        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeCreate + 1);
        Treatment testTreatment = treatmentList.get(treatmentList.size() - 1);
        assertThat(testTreatment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTreatment.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTreatment.getNextPlan()).isEqualTo(DEFAULT_NEXT_PLAN);
        assertThat(testTreatment.getRevisitDate()).isEqualTo(DEFAULT_REVISIT_DATE);
    }

    @Test
    @Transactional
    public void createTreatmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();

        // Create the Treatment with an existing ID
        treatment.setId(1L);
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = treatmentRepository.findAll().size();
        // set the field null
        treatment.setDate(null);

        // Create the Treatment, which fails.
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);


        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isBadRequest());

        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTreatments() throws Exception {
        // Initialize the database
        treatment.setState(TreatmentState.IN_PROCESS);
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList
        restTreatmentMockMvc.perform(get("/api/treatments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatment.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].nextPlan").value(hasItem(DEFAULT_NEXT_PLAN)))
            .andExpect(jsonPath("$.[*].revisitDate").value(hasItem(DEFAULT_REVISIT_DATE.toString())));
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTreatmentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(treatmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTreatmentMockMvc.perform(get("/api/treatments?eagerload=true"))
            .andExpect(status().isOk());

        verify(treatmentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTreatmentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(treatmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTreatmentMockMvc.perform(get("/api/treatments?eagerload=true"))
            .andExpect(status().isOk());

        verify(treatmentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTreatment() throws Exception {
        // Initialize the database
        treatment.setState(TreatmentState.IN_PROCESS);
        treatmentRepository.saveAndFlush(treatment);

        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", treatment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(treatment.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.nextPlan").value(DEFAULT_NEXT_PLAN))
            .andExpect(jsonPath("$.revisitDate").value(DEFAULT_REVISIT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTreatment() throws Exception {
        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatment() throws Exception {
        // Initialize the database
        treatment.setState(TreatmentState.IN_PROCESS);
        treatmentRepository.saveAndFlush(treatment);

        int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Update the treatment
        Treatment updatedTreatment = treatmentRepository.findById(treatment.getId()).get();
        // Disconnect from session so that the updates on updatedTreatment are not directly saved in db
        em.detach(updatedTreatment);
        updatedTreatment
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .nextPlan(UPDATED_NEXT_PLAN)
            .revisitDate(UPDATED_REVISIT_DATE);
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(updatedTreatment);

        restTreatmentMockMvc.perform(put("/api/treatments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isOk());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeUpdate);
        Treatment testTreatment = treatmentList.get(treatmentList.size() - 1);
        assertThat(testTreatment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTreatment.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTreatment.getNextPlan()).isEqualTo(UPDATED_NEXT_PLAN);
        assertThat(testTreatment.getRevisitDate()).isEqualTo(UPDATED_REVISIT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTreatment() throws Exception {
        int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Create the Treatment
        TreatmentDTO treatmentDTO = treatmentMapper.toDto(treatment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreatmentMockMvc.perform(put("/api/treatments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTreatment() throws Exception {
        // Initialize the database
        treatment.setState(TreatmentState.IN_PROCESS);
        treatmentRepository.saveAndFlush(treatment);

        int databaseSizeBeforeDelete = treatmentRepository.findAll().size();

        // Delete the treatment
        restTreatmentMockMvc.perform(delete("/api/treatments/{id}", treatment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
