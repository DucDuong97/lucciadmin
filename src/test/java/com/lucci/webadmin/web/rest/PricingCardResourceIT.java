package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.LucciadminApp;
import com.lucci.webadmin.domain.PricingCard;
import com.lucci.webadmin.repository.PricingCardRepository;
import com.lucci.webadmin.service.PricingCardService;
import com.lucci.webadmin.service.dto.PricingCardDTO;
import com.lucci.webadmin.service.mapper.PricingCardMapper;

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
 * Integration tests for the {@link PricingCardResource} REST controller.
 */
@SpringBootTest(classes = LucciadminApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PricingCardResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    @Autowired
    private PricingCardRepository pricingCardRepository;

    @Autowired
    private PricingCardMapper pricingCardMapper;

    @Autowired
    private PricingCardService pricingCardService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPricingCardMockMvc;

    private PricingCard pricingCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PricingCard createEntity(EntityManager em) {
        PricingCard pricingCard = new PricingCard()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE);
        return pricingCard;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PricingCard createUpdatedEntity(EntityManager em) {
        PricingCard pricingCard = new PricingCard()
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE);
        return pricingCard;
    }

    @BeforeEach
    public void initTest() {
        pricingCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createPricingCard() throws Exception {
        int databaseSizeBeforeCreate = pricingCardRepository.findAll().size();
        // Create the PricingCard
        PricingCardDTO pricingCardDTO = pricingCardMapper.toDto(pricingCard);
        restPricingCardMockMvc.perform(post("/api/pricing-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pricingCardDTO)))
            .andExpect(status().isCreated());

        // Validate the PricingCard in the database
        List<PricingCard> pricingCardList = pricingCardRepository.findAll();
        assertThat(pricingCardList).hasSize(databaseSizeBeforeCreate + 1);
        PricingCard testPricingCard = pricingCardList.get(pricingCardList.size() - 1);
        assertThat(testPricingCard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPricingCard.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createPricingCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pricingCardRepository.findAll().size();

        // Create the PricingCard with an existing ID
        pricingCard.setId(1L);
        PricingCardDTO pricingCardDTO = pricingCardMapper.toDto(pricingCard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPricingCardMockMvc.perform(post("/api/pricing-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pricingCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PricingCard in the database
        List<PricingCard> pricingCardList = pricingCardRepository.findAll();
        assertThat(pricingCardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricingCardRepository.findAll().size();
        // set the field null
        pricingCard.setName(null);

        // Create the PricingCard, which fails.
        PricingCardDTO pricingCardDTO = pricingCardMapper.toDto(pricingCard);


        restPricingCardMockMvc.perform(post("/api/pricing-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pricingCardDTO)))
            .andExpect(status().isBadRequest());

        List<PricingCard> pricingCardList = pricingCardRepository.findAll();
        assertThat(pricingCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricingCardRepository.findAll().size();
        // set the field null
        pricingCard.setPrice(null);

        // Create the PricingCard, which fails.
        PricingCardDTO pricingCardDTO = pricingCardMapper.toDto(pricingCard);


        restPricingCardMockMvc.perform(post("/api/pricing-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pricingCardDTO)))
            .andExpect(status().isBadRequest());

        List<PricingCard> pricingCardList = pricingCardRepository.findAll();
        assertThat(pricingCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPricingCards() throws Exception {
        // Initialize the database
        pricingCardRepository.saveAndFlush(pricingCard);

        // Get all the pricingCardList
        restPricingCardMockMvc.perform(get("/api/pricing-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pricingCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }
    
    @Test
    @Transactional
    public void getPricingCard() throws Exception {
        // Initialize the database
        pricingCardRepository.saveAndFlush(pricingCard);

        // Get the pricingCard
        restPricingCardMockMvc.perform(get("/api/pricing-cards/{id}", pricingCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pricingCard.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }
    @Test
    @Transactional
    public void getNonExistingPricingCard() throws Exception {
        // Get the pricingCard
        restPricingCardMockMvc.perform(get("/api/pricing-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePricingCard() throws Exception {
        // Initialize the database
        pricingCardRepository.saveAndFlush(pricingCard);

        int databaseSizeBeforeUpdate = pricingCardRepository.findAll().size();

        // Update the pricingCard
        PricingCard updatedPricingCard = pricingCardRepository.findById(pricingCard.getId()).get();
        // Disconnect from session so that the updates on updatedPricingCard are not directly saved in db
        em.detach(updatedPricingCard);
        updatedPricingCard
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE);
        PricingCardDTO pricingCardDTO = pricingCardMapper.toDto(updatedPricingCard);

        restPricingCardMockMvc.perform(put("/api/pricing-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pricingCardDTO)))
            .andExpect(status().isOk());

        // Validate the PricingCard in the database
        List<PricingCard> pricingCardList = pricingCardRepository.findAll();
        assertThat(pricingCardList).hasSize(databaseSizeBeforeUpdate);
        PricingCard testPricingCard = pricingCardList.get(pricingCardList.size() - 1);
        assertThat(testPricingCard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPricingCard.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingPricingCard() throws Exception {
        int databaseSizeBeforeUpdate = pricingCardRepository.findAll().size();

        // Create the PricingCard
        PricingCardDTO pricingCardDTO = pricingCardMapper.toDto(pricingCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPricingCardMockMvc.perform(put("/api/pricing-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pricingCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PricingCard in the database
        List<PricingCard> pricingCardList = pricingCardRepository.findAll();
        assertThat(pricingCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePricingCard() throws Exception {
        // Initialize the database
        pricingCardRepository.saveAndFlush(pricingCard);

        int databaseSizeBeforeDelete = pricingCardRepository.findAll().size();

        // Delete the pricingCard
        restPricingCardMockMvc.perform(delete("/api/pricing-cards/{id}", pricingCard.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PricingCard> pricingCardList = pricingCardRepository.findAll();
        assertThat(pricingCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
