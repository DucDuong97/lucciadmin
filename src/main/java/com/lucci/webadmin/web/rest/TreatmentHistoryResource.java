package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.TreatmentHistory;
import com.lucci.webadmin.service.TreatmentHistoryService;
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
 * REST controller for managing {@link com.lucci.webadmin.domain.TreatmentHistory}.
 */
@RestController
@RequestMapping("/api")
public class TreatmentHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentHistoryResource.class);

    private static final String ENTITY_NAME = "treatmentHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TreatmentHistoryService treatmentHistoryService;

    public TreatmentHistoryResource(TreatmentHistoryService treatmentHistoryService) {
        this.treatmentHistoryService = treatmentHistoryService;
    }

    /**
     * {@code POST  /treatment-histories} : Create a new treatmentHistory.
     *
     * @param treatmentHistory the treatmentHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new treatmentHistory, or with status {@code 400 (Bad Request)} if the treatmentHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/treatment-histories")
    public ResponseEntity<TreatmentHistory> createTreatmentHistory(@Valid @RequestBody TreatmentHistory treatmentHistory) throws URISyntaxException {
        log.debug("REST request to save TreatmentHistory : {}", treatmentHistory);
        if (treatmentHistory.getId() != null) {
            throw new BadRequestAlertException("A new treatmentHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TreatmentHistory result = treatmentHistoryService.save(treatmentHistory);
        return ResponseEntity.created(new URI("/api/treatment-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /treatment-histories} : Updates an existing treatmentHistory.
     *
     * @param treatmentHistory the treatmentHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated treatmentHistory,
     * or with status {@code 400 (Bad Request)} if the treatmentHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the treatmentHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/treatment-histories")
    public ResponseEntity<TreatmentHistory> updateTreatmentHistory(@Valid @RequestBody TreatmentHistory treatmentHistory) throws URISyntaxException {
        log.debug("REST request to update TreatmentHistory : {}", treatmentHistory);
        if (treatmentHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TreatmentHistory result = treatmentHistoryService.save(treatmentHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, treatmentHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /treatment-histories} : get all the treatmentHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of treatmentHistories in body.
     */
    @GetMapping("/treatment-histories")
    public List<TreatmentHistory> getAllTreatmentHistories() {
        log.debug("REST request to get all TreatmentHistories");
        return treatmentHistoryService.findAll();
    }

    /**
     * {@code GET  /treatment-histories/:id} : get the "id" treatmentHistory.
     *
     * @param id the id of the treatmentHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the treatmentHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/treatment-histories/{id}")
    public ResponseEntity<TreatmentHistory> getTreatmentHistory(@PathVariable Long id) {
        log.debug("REST request to get TreatmentHistory : {}", id);
        Optional<TreatmentHistory> treatmentHistory = treatmentHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(treatmentHistory);
    }

    /**
     * {@code DELETE  /treatment-histories/:id} : delete the "id" treatmentHistory.
     *
     * @param id the id of the treatmentHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/treatment-histories/{id}")
    public ResponseEntity<Void> deleteTreatmentHistory(@PathVariable Long id) {
        log.debug("REST request to delete TreatmentHistory : {}", id);
        treatmentHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
