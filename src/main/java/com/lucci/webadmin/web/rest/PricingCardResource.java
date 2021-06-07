package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.PricingCard;
import com.lucci.webadmin.service.PricingCardService;
import com.lucci.webadmin.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lucci.webadmin.domain.PricingCard}.
 */
@RestController
@RequestMapping("/api")
public class PricingCardResource {

    private final Logger log = LoggerFactory.getLogger(PricingCardResource.class);

    private static final String ENTITY_NAME = "pricingCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PricingCardService pricingCardService;

    public PricingCardResource(PricingCardService pricingCardService) {
        this.pricingCardService = pricingCardService;
    }

    /**
     * {@code POST  /pricing-cards} : Create a new pricingCard.
     *
     * @param pricingCard the pricingCard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pricingCard, or with status {@code 400 (Bad Request)} if the pricingCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pricing-cards")
    public ResponseEntity<PricingCard> createPricingCard(@Valid @RequestBody PricingCard pricingCard) throws URISyntaxException {
        log.debug("REST request to save PricingCard : {}", pricingCard);
        if (pricingCard.getId() != null) {
            throw new BadRequestAlertException("A new pricingCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PricingCard result = pricingCardService.save(pricingCard);
        return ResponseEntity.created(new URI("/api/pricing-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pricing-cards} : Updates an existing pricingCard.
     *
     * @param pricingCard the pricingCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pricingCard,
     * or with status {@code 400 (Bad Request)} if the pricingCard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pricingCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pricing-cards")
    public ResponseEntity<PricingCard> updatePricingCard(@Valid @RequestBody PricingCard pricingCard) throws URISyntaxException {
        log.debug("REST request to update PricingCard : {}", pricingCard);
        if (pricingCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PricingCard result = pricingCardService.save(pricingCard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pricingCard.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pricing-cards} : get all the pricingCards.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pricingCards in body.
     */
    @CrossOrigin
    @GetMapping("/pricing-cards")
    public List<PricingCard> getAllPricingCards() {
        log.debug("REST request to get all PricingCards");
        return pricingCardService.findAll();
    }

    @CrossOrigin
    @GetMapping("/pricing-cards/service/{id}")
    public List<PricingCard> getAllPricingCardsByServiceId(@PathVariable Long id) {
        log.debug("REST request to get all PricingCards by Service Id: {}", id);
        return pricingCardService.findAllByServiceId(id);
    }

    /**
     * {@code GET  /pricing-cards/:id} : get the "id" pricingCard.
     *
     * @param id the id of the pricingCard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pricingCard, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/pricing-cards/{id}")
    public ResponseEntity<PricingCard> getPricingCard(@PathVariable Long id) {
        log.debug("REST request to get PricingCard : {}", id);
        Optional<PricingCard> pricingCard = pricingCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pricingCard);
    }

    /**
     * {@code DELETE  /pricing-cards/:id} : delete the "id" pricingCard.
     *
     * @param id the id of the pricingCard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pricing-cards/{id}")
    public ResponseEntity<Void> deletePricingCard(@PathVariable Long id) {
        log.debug("REST request to delete PricingCard : {}", id);
        pricingCardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
