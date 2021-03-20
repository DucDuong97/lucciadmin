package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.Achievement;
import com.lucci.webadmin.repository.AchievementRepository;
import com.lucci.webadmin.service.AchievementService;

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
 * Integration tests for the {@link AchievementResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AchievementResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final String DEFAULT_IMG_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMG_URL = "BBBBBBBBBB";

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAchievementMockMvc;

    private Achievement achievement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Achievement createEntity(EntityManager em) {
        Achievement achievement = new Achievement()
            .name(DEFAULT_NAME)
            .number(DEFAULT_NUMBER)
            .imgUrl(DEFAULT_IMG_URL);
        return achievement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Achievement createUpdatedEntity(EntityManager em) {
        Achievement achievement = new Achievement()
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .imgUrl(UPDATED_IMG_URL);
        return achievement;
    }

    @BeforeEach
    public void initTest() {
        achievement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAchievement() throws Exception {
        int databaseSizeBeforeCreate = achievementRepository.findAll().size();
        // Create the Achievement
        restAchievementMockMvc.perform(post("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(achievement)))
            .andExpect(status().isCreated());

        // Validate the Achievement in the database
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeCreate + 1);
        Achievement testAchievement = achievementList.get(achievementList.size() - 1);
        assertThat(testAchievement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAchievement.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testAchievement.getImgUrl()).isEqualTo(DEFAULT_IMG_URL);
    }

    @Test
    @Transactional
    public void createAchievementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = achievementRepository.findAll().size();

        // Create the Achievement with an existing ID
        achievement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAchievementMockMvc.perform(post("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(achievement)))
            .andExpect(status().isBadRequest());

        // Validate the Achievement in the database
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = achievementRepository.findAll().size();
        // set the field null
        achievement.setName(null);

        // Create the Achievement, which fails.


        restAchievementMockMvc.perform(post("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(achievement)))
            .andExpect(status().isBadRequest());

        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = achievementRepository.findAll().size();
        // set the field null
        achievement.setNumber(null);

        // Create the Achievement, which fails.


        restAchievementMockMvc.perform(post("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(achievement)))
            .andExpect(status().isBadRequest());

        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImgUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = achievementRepository.findAll().size();
        // set the field null
        achievement.setImgUrl(null);

        // Create the Achievement, which fails.


        restAchievementMockMvc.perform(post("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(achievement)))
            .andExpect(status().isBadRequest());

        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAchievements() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList
        restAchievementMockMvc.perform(get("/api/achievements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(achievement.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(DEFAULT_IMG_URL)));
    }
    
    @Test
    @Transactional
    public void getAchievement() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get the achievement
        restAchievementMockMvc.perform(get("/api/achievements/{id}", achievement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(achievement.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.imgUrl").value(DEFAULT_IMG_URL));
    }
    @Test
    @Transactional
    public void getNonExistingAchievement() throws Exception {
        // Get the achievement
        restAchievementMockMvc.perform(get("/api/achievements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAchievement() throws Exception {
        // Initialize the database
        achievementService.save(achievement);

        int databaseSizeBeforeUpdate = achievementRepository.findAll().size();

        // Update the achievement
        Achievement updatedAchievement = achievementRepository.findById(achievement.getId()).get();
        // Disconnect from session so that the updates on updatedAchievement are not directly saved in db
        em.detach(updatedAchievement);
        updatedAchievement
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .imgUrl(UPDATED_IMG_URL);

        restAchievementMockMvc.perform(put("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAchievement)))
            .andExpect(status().isOk());

        // Validate the Achievement in the database
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeUpdate);
        Achievement testAchievement = achievementList.get(achievementList.size() - 1);
        assertThat(testAchievement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAchievement.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testAchievement.getImgUrl()).isEqualTo(UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingAchievement() throws Exception {
        int databaseSizeBeforeUpdate = achievementRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAchievementMockMvc.perform(put("/api/achievements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(achievement)))
            .andExpect(status().isBadRequest());

        // Validate the Achievement in the database
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAchievement() throws Exception {
        // Initialize the database
        achievementService.save(achievement);

        int databaseSizeBeforeDelete = achievementRepository.findAll().size();

        // Delete the achievement
        restAchievementMockMvc.perform(delete("/api/achievements/{id}", achievement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
