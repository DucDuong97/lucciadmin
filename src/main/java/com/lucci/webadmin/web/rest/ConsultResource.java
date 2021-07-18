package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.service.ConsultService;
import com.lucci.webadmin.web.rest.errors.BadRequestAlertException;
import com.lucci.webadmin.service.dto.ConsultDTO;

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
 * REST controller for managing {@link com.lucci.webadmin.domain.Consult}.
 */
@RestController
@RequestMapping("/api")
public class ConsultResource {

    private final Logger log = LoggerFactory.getLogger(ConsultResource.class);

    private static final String ENTITY_NAME = "consult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsultService consultService;

    public ConsultResource(ConsultService consultService) {
        this.consultService = consultService;
    }

    /**
     * {@code POST  /consults} : Create a new consult.
     *
     * @param consultDTO the consultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consultDTO, or with status {@code 400 (Bad Request)} if the consult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consults")
    public ResponseEntity<ConsultDTO> createConsult(@Valid @RequestBody ConsultDTO consultDTO) throws URISyntaxException {
        log.debug("REST request to save Consult : {}", consultDTO);
        if (consultDTO.getId() != null) {
            throw new BadRequestAlertException("A new consult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultDTO result = consultService.save(consultDTO);
        return ResponseEntity.created(new URI("/api/consults/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consults} : Updates an existing consult.
     *
     * @param consultDTO the consultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultDTO,
     * or with status {@code 400 (Bad Request)} if the consultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consults")
    public ResponseEntity<ConsultDTO> updateConsult(@Valid @RequestBody ConsultDTO consultDTO) throws URISyntaxException {
        log.debug("REST request to update Consult : {}", consultDTO);
        if (consultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsultDTO result = consultService.save(consultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consults} : get all the consults.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consults in body.
     */
    @GetMapping("/consults")
    public ResponseEntity<List<ConsultDTO>> getAllConsults(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Consults");
        Page<ConsultDTO> page;
        if (eagerload) {
            page = consultService.findAllWithEagerRelationships(pageable);
        } else {
            page = consultService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consults/:id} : get the "id" consult.
     *
     * @param id the id of the consultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consults/{id}")
    public ResponseEntity<ConsultDTO> getConsult(@PathVariable Long id) {
        log.debug("REST request to get Consult : {}", id);
        Optional<ConsultDTO> consultDTO = consultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consultDTO);
    }

    /**
     * {@code DELETE  /consults/:id} : delete the "id" consult.
     *
     * @param id the id of the consultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consults/{id}")
    public ResponseEntity<Void> deleteConsult(@PathVariable Long id) {
        log.debug("REST request to delete Consult : {}", id);
        consultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
