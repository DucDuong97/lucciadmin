package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.Nurse;
import com.lucci.webadmin.service.NurseService;
import com.lucci.webadmin.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lucci.webadmin.domain.Nurse}.
 */
@RestController
@RequestMapping("/api")
public class NurseResource {

    private final Logger log = LoggerFactory.getLogger(NurseResource.class);

    private static final String ENTITY_NAME = "nurse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NurseService nurseService;

    public NurseResource(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    /**
     * {@code POST  /nurses} : Create a new nurse.
     *
     * @param nurse the nurse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nurse, or with status {@code 400 (Bad Request)} if the nurse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nurses")
    public ResponseEntity<Nurse> createNurse(@RequestBody Nurse nurse) throws URISyntaxException {
        log.debug("REST request to save Nurse : {}", nurse);
        if (nurse.getId() != null) {
            throw new BadRequestAlertException("A new nurse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nurse result = nurseService.save(nurse);
        return ResponseEntity.created(new URI("/api/nurses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nurses} : Updates an existing nurse.
     *
     * @param nurse the nurse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nurse,
     * or with status {@code 400 (Bad Request)} if the nurse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nurse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nurses")
    public ResponseEntity<Nurse> updateNurse(@RequestBody Nurse nurse) throws URISyntaxException {
        log.debug("REST request to update Nurse : {}", nurse);
        if (nurse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Nurse result = nurseService.save(nurse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nurse.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nurses} : get all the nurses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nurses in body.
     */
    @GetMapping("/nurses")
    public List<Nurse> getAllNurses() {
        log.debug("REST request to get all Nurses");
        return nurseService.findAll();
    }

    /**
     * {@code GET  /nurses/:id} : get the "id" nurse.
     *
     * @param id the id of the nurse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nurse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nurses/{id}")
    public ResponseEntity<Nurse> getNurse(@PathVariable Long id) {
        log.debug("REST request to get Nurse : {}", id);
        Optional<Nurse> nurse = nurseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nurse);
    }

    /**
     * {@code DELETE  /nurses/:id} : delete the "id" nurse.
     *
     * @param id the id of the nurse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nurses/{id}")
    public ResponseEntity<Void> deleteNurse(@PathVariable Long id) {
        log.debug("REST request to delete Nurse : {}", id);
        nurseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
