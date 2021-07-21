package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.ImgUrl;
import com.lucci.webadmin.repository.ImgUrlRepository;
import com.lucci.webadmin.service.ImgUrlService;
import com.lucci.webadmin.service.dto.ImgUrlDTO;
import com.lucci.webadmin.service.mapper.ImgUrlMapper;

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
 * Integration tests for the {@link ImgUrlResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ImgUrlResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    @Autowired
    private ImgUrlRepository imgUrlRepository;

    @Autowired
    private ImgUrlMapper imgUrlMapper;

    @Autowired
    private ImgUrlService imgUrlService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImgUrlMockMvc;

    private ImgUrl imgUrl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImgUrl createEntity(EntityManager em) {
        ImgUrl imgUrl = new ImgUrl()
            .name(DEFAULT_NAME)
            .path(DEFAULT_PATH);
        return imgUrl;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImgUrl createUpdatedEntity(EntityManager em) {
        ImgUrl imgUrl = new ImgUrl()
            .name(UPDATED_NAME)
            .path(UPDATED_PATH);
        return imgUrl;
    }

    @BeforeEach
    public void initTest() {
        imgUrl = createEntity(em);
    }

    @Test
    @Transactional
    public void createImgUrl() throws Exception {
        int databaseSizeBeforeCreate = imgUrlRepository.findAll().size();
        // Create the ImgUrl
        ImgUrlDTO imgUrlDTO = imgUrlMapper.toDto(imgUrl);
        restImgUrlMockMvc.perform(post("/api/img-urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imgUrlDTO)))
            .andExpect(status().isCreated());

        // Validate the ImgUrl in the database
        List<ImgUrl> imgUrlList = imgUrlRepository.findAll();
        assertThat(imgUrlList).hasSize(databaseSizeBeforeCreate + 1);
        ImgUrl testImgUrl = imgUrlList.get(imgUrlList.size() - 1);
        assertThat(testImgUrl.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testImgUrl.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    public void createImgUrlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imgUrlRepository.findAll().size();

        // Create the ImgUrl with an existing ID
        imgUrl.setId(1L);
        ImgUrlDTO imgUrlDTO = imgUrlMapper.toDto(imgUrl);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImgUrlMockMvc.perform(post("/api/img-urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imgUrlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImgUrl in the database
        List<ImgUrl> imgUrlList = imgUrlRepository.findAll();
        assertThat(imgUrlList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = imgUrlRepository.findAll().size();
        // set the field null
        imgUrl.setName(null);

        // Create the ImgUrl, which fails.
        ImgUrlDTO imgUrlDTO = imgUrlMapper.toDto(imgUrl);


        restImgUrlMockMvc.perform(post("/api/img-urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imgUrlDTO)))
            .andExpect(status().isBadRequest());

        List<ImgUrl> imgUrlList = imgUrlRepository.findAll();
        assertThat(imgUrlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = imgUrlRepository.findAll().size();
        // set the field null
        imgUrl.setPath(null);

        // Create the ImgUrl, which fails.
        ImgUrlDTO imgUrlDTO = imgUrlMapper.toDto(imgUrl);


        restImgUrlMockMvc.perform(post("/api/img-urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imgUrlDTO)))
            .andExpect(status().isBadRequest());

        List<ImgUrl> imgUrlList = imgUrlRepository.findAll();
        assertThat(imgUrlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImgUrls() throws Exception {
        // Initialize the database
        imgUrlRepository.saveAndFlush(imgUrl);

        // Get all the imgUrlList
        restImgUrlMockMvc.perform(get("/api/img-urls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imgUrl.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)));
    }
    
    @Test
    @Transactional
    public void getImgUrl() throws Exception {
        // Initialize the database
        imgUrlRepository.saveAndFlush(imgUrl);

        // Get the imgUrl
        restImgUrlMockMvc.perform(get("/api/img-urls/{id}", imgUrl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(imgUrl.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH));
    }
    @Test
    @Transactional
    public void getNonExistingImgUrl() throws Exception {
        // Get the imgUrl
        restImgUrlMockMvc.perform(get("/api/img-urls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImgUrl() throws Exception {
        // Initialize the database
        imgUrlRepository.saveAndFlush(imgUrl);

        int databaseSizeBeforeUpdate = imgUrlRepository.findAll().size();

        // Update the imgUrl
        ImgUrl updatedImgUrl = imgUrlRepository.findById(imgUrl.getId()).get();
        // Disconnect from session so that the updates on updatedImgUrl are not directly saved in db
        em.detach(updatedImgUrl);
        updatedImgUrl
            .name(UPDATED_NAME)
            .path(UPDATED_PATH);
        ImgUrlDTO imgUrlDTO = imgUrlMapper.toDto(updatedImgUrl);

        restImgUrlMockMvc.perform(put("/api/img-urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imgUrlDTO)))
            .andExpect(status().isOk());

        // Validate the ImgUrl in the database
        List<ImgUrl> imgUrlList = imgUrlRepository.findAll();
        assertThat(imgUrlList).hasSize(databaseSizeBeforeUpdate);
        ImgUrl testImgUrl = imgUrlList.get(imgUrlList.size() - 1);
        assertThat(testImgUrl.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testImgUrl.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingImgUrl() throws Exception {
        int databaseSizeBeforeUpdate = imgUrlRepository.findAll().size();

        // Create the ImgUrl
        ImgUrlDTO imgUrlDTO = imgUrlMapper.toDto(imgUrl);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImgUrlMockMvc.perform(put("/api/img-urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imgUrlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImgUrl in the database
        List<ImgUrl> imgUrlList = imgUrlRepository.findAll();
        assertThat(imgUrlList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImgUrl() throws Exception {
        // Initialize the database
        imgUrlRepository.saveAndFlush(imgUrl);

        int databaseSizeBeforeDelete = imgUrlRepository.findAll().size();

        // Delete the imgUrl
        restImgUrlMockMvc.perform(delete("/api/img-urls/{id}", imgUrl.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ImgUrl> imgUrlList = imgUrlRepository.findAll();
        assertThat(imgUrlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
