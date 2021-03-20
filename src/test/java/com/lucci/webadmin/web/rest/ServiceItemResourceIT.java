package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.ServiceItem;
import com.lucci.webadmin.repository.ServiceItemRepository;
import com.lucci.webadmin.service.ServiceItemService;

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
 * Integration tests for the {@link ServiceItemResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMG_URL = "BBBBBBBBBB";

    @Autowired
    private ServiceItemRepository serviceItemRepository;

    @Autowired
    private ServiceItemService serviceItemService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceItemMockMvc;

    private ServiceItem serviceItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceItem createEntity(EntityManager em) {
        ServiceItem serviceItem = new ServiceItem()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .imgUrl(DEFAULT_IMG_URL);
        return serviceItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceItem createUpdatedEntity(EntityManager em) {
        ServiceItem serviceItem = new ServiceItem()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .imgUrl(UPDATED_IMG_URL);
        return serviceItem;
    }

    @BeforeEach
    public void initTest() {
        serviceItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceItem() throws Exception {
        int databaseSizeBeforeCreate = serviceItemRepository.findAll().size();
        // Create the ServiceItem
        restServiceItemMockMvc.perform(post("/api/service-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceItem)))
            .andExpect(status().isCreated());

        // Validate the ServiceItem in the database
        List<ServiceItem> serviceItemList = serviceItemRepository.findAll();
        assertThat(serviceItemList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceItem testServiceItem = serviceItemList.get(serviceItemList.size() - 1);
        assertThat(testServiceItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testServiceItem.getImgUrl()).isEqualTo(DEFAULT_IMG_URL);
    }

    @Test
    @Transactional
    public void createServiceItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceItemRepository.findAll().size();

        // Create the ServiceItem with an existing ID
        serviceItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceItemMockMvc.perform(post("/api/service-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceItem)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceItem in the database
        List<ServiceItem> serviceItemList = serviceItemRepository.findAll();
        assertThat(serviceItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceItemRepository.findAll().size();
        // set the field null
        serviceItem.setName(null);

        // Create the ServiceItem, which fails.


        restServiceItemMockMvc.perform(post("/api/service-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceItem)))
            .andExpect(status().isBadRequest());

        List<ServiceItem> serviceItemList = serviceItemRepository.findAll();
        assertThat(serviceItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceItems() throws Exception {
        // Initialize the database
        serviceItemRepository.saveAndFlush(serviceItem);

        // Get all the serviceItemList
        restServiceItemMockMvc.perform(get("/api/service-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(DEFAULT_IMG_URL)));
    }
    
    @Test
    @Transactional
    public void getServiceItem() throws Exception {
        // Initialize the database
        serviceItemRepository.saveAndFlush(serviceItem);

        // Get the serviceItem
        restServiceItemMockMvc.perform(get("/api/service-items/{id}", serviceItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imgUrl").value(DEFAULT_IMG_URL));
    }
    @Test
    @Transactional
    public void getNonExistingServiceItem() throws Exception {
        // Get the serviceItem
        restServiceItemMockMvc.perform(get("/api/service-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceItem() throws Exception {
        // Initialize the database
        serviceItemService.save(serviceItem);

        int databaseSizeBeforeUpdate = serviceItemRepository.findAll().size();

        // Update the serviceItem
        ServiceItem updatedServiceItem = serviceItemRepository.findById(serviceItem.getId()).get();
        // Disconnect from session so that the updates on updatedServiceItem are not directly saved in db
        em.detach(updatedServiceItem);
        updatedServiceItem
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .imgUrl(UPDATED_IMG_URL);

        restServiceItemMockMvc.perform(put("/api/service-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceItem)))
            .andExpect(status().isOk());

        // Validate the ServiceItem in the database
        List<ServiceItem> serviceItemList = serviceItemRepository.findAll();
        assertThat(serviceItemList).hasSize(databaseSizeBeforeUpdate);
        ServiceItem testServiceItem = serviceItemList.get(serviceItemList.size() - 1);
        assertThat(testServiceItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServiceItem.getImgUrl()).isEqualTo(UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceItem() throws Exception {
        int databaseSizeBeforeUpdate = serviceItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceItemMockMvc.perform(put("/api/service-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceItem)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceItem in the database
        List<ServiceItem> serviceItemList = serviceItemRepository.findAll();
        assertThat(serviceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceItem() throws Exception {
        // Initialize the database
        serviceItemService.save(serviceItem);

        int databaseSizeBeforeDelete = serviceItemRepository.findAll().size();

        // Delete the serviceItem
        restServiceItemMockMvc.perform(delete("/api/service-items/{id}", serviceItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceItem> serviceItemList = serviceItemRepository.findAll();
        assertThat(serviceItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
