package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.service.PricingContentService;
import com.lucci.webadmin.web.rest.errors.BadRequestAlertException;
import com.lucci.webadmin.service.dto.PricingContentDTO;

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
 * REST controller for managing {@link com.lucci.webadmin.domain.PricingContent}.
 */
@RestController
@RequestMapping("/api")
public class PricingContentResource {

    private final Logger log = LoggerFactory.getLogger(PricingContentResource.class);

    private static final String ENTITY_NAME = "pricingContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PricingContentService pricingContentService;

    public PricingContentResource(PricingContentService pricingContentService) {
        this.pricingContentService = pricingContentService;
    }

    /**
     * {@code POST  /pricing-contents} : Create a new pricingContent.
     *
     * @param pricingContentDTO the pricingContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pricingContentDTO, or with status {@code 400 (Bad Request)} if the pricingContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pricing-contents")
    public ResponseEntity<PricingContentDTO> createPricingContent(@Valid @RequestBody PricingContentDTO pricingContentDTO) throws URISyntaxException {
        log.debug("REST request to save PricingContent : {}", pricingContentDTO);
        if (pricingContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new pricingContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PricingContentDTO result = pricingContentService.save(pricingContentDTO);
        return ResponseEntity.created(new URI("/api/pricing-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pricing-contents} : Updates an existing pricingContent.
     *
     * @param pricingContentDTO the pricingContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pricingContentDTO,
     * or with status {@code 400 (Bad Request)} if the pricingContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pricingContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pricing-contents")
    public ResponseEntity<PricingContentDTO> updatePricingContent(@Valid @RequestBody PricingContentDTO pricingContentDTO) throws URISyntaxException {
        log.debug("REST request to update PricingContent : {}", pricingContentDTO);
        if (pricingContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PricingContentDTO result = pricingContentService.save(pricingContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pricingContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pricing-contents} : get all the pricingContents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pricingContents in body.
     */
    @CrossOrigin
    @GetMapping("/pricing-contents")
    public List<PricingContentDTO> getAllPricingContents() {
        log.debug("REST request to get all PricingContents");
        return pricingContentService.findAll();
    }

    /**
     * {@code GET  /pricing-contents/:id} : get the "id" pricingContent.
     *
     * @param id the id of the pricingContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pricingContentDTO, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/pricing-contents/{id}")
    public ResponseEntity<PricingContentDTO> getPricingContent(@PathVariable Long id) {
        log.debug("REST request to get PricingContent : {}", id);
        Optional<PricingContentDTO> pricingContentDTO = pricingContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pricingContentDTO);
    }

    /**
     * {@code DELETE  /pricing-contents/:id} : delete the "id" pricingContent.
     *
     * @param id the id of the pricingContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pricing-contents/{id}")
    public ResponseEntity<Void> deletePricingContent(@PathVariable Long id) {
        log.debug("REST request to delete PricingContent : {}", id);
        pricingContentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
