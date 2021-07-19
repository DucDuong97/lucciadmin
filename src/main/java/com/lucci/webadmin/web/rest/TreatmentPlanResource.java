package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.service.TreatmentPlanService;
import com.lucci.webadmin.web.rest.errors.BadRequestAlertException;
import com.lucci.webadmin.service.dto.TreatmentPlanDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lucci.webadmin.domain.TreatmentPlan}.
 */
@RestController
@RequestMapping("/api")
public class TreatmentPlanResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentPlanResource.class);

    private static final String ENTITY_NAME = "treatmentPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TreatmentPlanService treatmentPlanService;

    public TreatmentPlanResource(TreatmentPlanService treatmentPlanService) {
        this.treatmentPlanService = treatmentPlanService;
    }

    /**
     * {@code POST  /treatment-plans} : Create a new treatmentPlan.
     *
     * @param treatmentPlanDTO the treatmentPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new treatmentPlanDTO, or with status {@code 400 (Bad Request)} if the treatmentPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/treatment-plans")
    public ResponseEntity<TreatmentPlanDTO> createTreatmentPlan(@Valid @RequestBody TreatmentPlanDTO treatmentPlanDTO) throws URISyntaxException {
        log.debug("REST request to save TreatmentPlan : {}", treatmentPlanDTO);
        if (treatmentPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new treatmentPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TreatmentPlanDTO result = treatmentPlanService.save(treatmentPlanDTO);
        return ResponseEntity.created(new URI("/api/treatment-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /treatment-plans} : Updates an existing treatmentPlan.
     *
     * @param treatmentPlanDTO the treatmentPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated treatmentPlanDTO,
     * or with status {@code 400 (Bad Request)} if the treatmentPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the treatmentPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/treatment-plans")
    public ResponseEntity<TreatmentPlanDTO> updateTreatmentPlan(@Valid @RequestBody TreatmentPlanDTO treatmentPlanDTO) throws URISyntaxException {
        log.debug("REST request to update TreatmentPlan : {}", treatmentPlanDTO);
        if (treatmentPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TreatmentPlanDTO result = treatmentPlanService.save(treatmentPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, treatmentPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /treatment-plans} : get all the treatmentPlans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of treatmentPlans in body.
     */
    @GetMapping("/treatment-plans")
    public ResponseEntity<List<TreatmentPlanDTO>> getAllTreatmentPlans(Pageable pageable) {
        log.debug("REST request to get a page of TreatmentPlans");
        Page<TreatmentPlanDTO> page = treatmentPlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /treatment-plans/:id} : get the "id" treatmentPlan.
     *
     * @param id the id of the treatmentPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the treatmentPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/treatment-plans/{id}")
    public ResponseEntity<TreatmentPlanDTO> getTreatmentPlan(@PathVariable Long id) {
        log.debug("REST request to get TreatmentPlan : {}", id);
        Optional<TreatmentPlanDTO> treatmentPlanDTO = treatmentPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(treatmentPlanDTO);
    }

    /**
     * {@code DELETE  /treatment-plans/:id} : delete the "id" treatmentPlan.
     *
     * @param id the id of the treatmentPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/treatment-plans/{id}")
    public ResponseEntity<Void> deleteTreatmentPlan(@PathVariable Long id) {
        log.debug("REST request to delete TreatmentPlan : {}", id);
        treatmentPlanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
