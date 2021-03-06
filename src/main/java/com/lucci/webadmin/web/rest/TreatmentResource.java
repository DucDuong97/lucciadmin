package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.service.TreatmentService;
import com.lucci.webadmin.web.rest.errors.BadRequestAlertException;
import com.lucci.webadmin.service.dto.TreatmentDTO;

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
 * REST controller for managing {@link com.lucci.webadmin.domain.Treatment}.
 */
@RestController
@RequestMapping("/api")
public class TreatmentResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentResource.class);

    private static final String ENTITY_NAME = "treatment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TreatmentService treatmentService;

    public TreatmentResource(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    /**
     * {@code POST  /treatments} : Create a new treatment.
     *
     * @param treatmentDTO the treatmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new treatmentDTO, or with status {@code 400 (Bad Request)} if the treatment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/treatments")
    public ResponseEntity<TreatmentDTO> createTreatment(@Valid @RequestBody TreatmentDTO treatmentDTO) throws URISyntaxException {
        log.debug("REST request to save Treatment : {}", treatmentDTO);
        if (treatmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new treatment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TreatmentDTO result = treatmentService.save(treatmentDTO);
        return ResponseEntity.created(new URI("/api/treatments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /treatments} : Updates an existing treatment.
     *
     * @param treatmentDTO the treatmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated treatmentDTO,
     * or with status {@code 400 (Bad Request)} if the treatmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the treatmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/treatments")
    public ResponseEntity<TreatmentDTO> updateTreatment(@Valid @RequestBody TreatmentDTO treatmentDTO) throws URISyntaxException {
        log.debug("REST request to update Treatment : {}", treatmentDTO);
        if (treatmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TreatmentDTO result = treatmentService.save(treatmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, treatmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /treatments} : get all the treatments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of treatments in body.
     */
    @GetMapping("/treatments")
    public ResponseEntity<List<TreatmentDTO>> getAllTreatments(Pageable pageable, Long planId) {
        log.debug("REST request to get a page of Treatments");
        Page<TreatmentDTO> page;
        page = planId != null ? treatmentService.findByPlanId(pageable, planId) : treatmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /treatments/:id} : get the "id" treatment.
     *
     * @param id the id of the treatmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the treatmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/treatments/{id}")
    public ResponseEntity<TreatmentDTO> getTreatment(@PathVariable Long id) {
        log.debug("REST request to get Treatment : {}", id);
        Optional<TreatmentDTO> treatmentDTO = treatmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(treatmentDTO);
    }

    /**
     * {@code DELETE  /treatments/:id} : delete the "id" treatment.
     *
     * @param id the id of the treatmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/treatments/{id}")
    public ResponseEntity<Void> deleteTreatment(@PathVariable Long id) {
        log.debug("REST request to delete Treatment : {}", id);
        treatmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
