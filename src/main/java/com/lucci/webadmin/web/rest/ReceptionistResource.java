package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.Receptionist;
import com.lucci.webadmin.service.ReceptionistService;
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
 * REST controller for managing {@link com.lucci.webadmin.domain.Receptionist}.
 */
@RestController
@RequestMapping("/api")
public class ReceptionistResource {

    private final Logger log = LoggerFactory.getLogger(ReceptionistResource.class);

    private static final String ENTITY_NAME = "receptionist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceptionistService receptionistService;

    public ReceptionistResource(ReceptionistService receptionistService) {
        this.receptionistService = receptionistService;
    }

    /**
     * {@code POST  /receptionists} : Create a new receptionist.
     *
     * @param receptionist the receptionist to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receptionist, or with status {@code 400 (Bad Request)} if the receptionist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/receptionists")
    public ResponseEntity<Receptionist> createReceptionist(@RequestBody Receptionist receptionist) throws URISyntaxException {
        log.debug("REST request to save Receptionist : {}", receptionist);
        if (receptionist.getId() != null) {
            throw new BadRequestAlertException("A new receptionist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Receptionist result = receptionistService.save(receptionist);
        return ResponseEntity.created(new URI("/api/receptionists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /receptionists} : Updates an existing receptionist.
     *
     * @param receptionist the receptionist to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receptionist,
     * or with status {@code 400 (Bad Request)} if the receptionist is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receptionist couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/receptionists")
    public ResponseEntity<Receptionist> updateReceptionist(@RequestBody Receptionist receptionist) throws URISyntaxException {
        log.debug("REST request to update Receptionist : {}", receptionist);
        if (receptionist.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Receptionist result = receptionistService.save(receptionist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, receptionist.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /receptionists} : get all the receptionists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of receptionists in body.
     */
    @GetMapping("/receptionists")
    public List<Receptionist> getAllReceptionists() {
        log.debug("REST request to get all Receptionists");
        return receptionistService.findAll();
    }

    /**
     * {@code GET  /receptionists/:id} : get the "id" receptionist.
     *
     * @param id the id of the receptionist to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receptionist, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/receptionists/{id}")
    public ResponseEntity<Receptionist> getReceptionist(@PathVariable Long id) {
        log.debug("REST request to get Receptionist : {}", id);
        Optional<Receptionist> receptionist = receptionistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(receptionist);
    }

    /**
     * {@code DELETE  /receptionists/:id} : delete the "id" receptionist.
     *
     * @param id the id of the receptionist to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/receptionists/{id}")
    public ResponseEntity<Void> deleteReceptionist(@PathVariable Long id) {
        log.debug("REST request to delete Receptionist : {}", id);
        receptionistService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
