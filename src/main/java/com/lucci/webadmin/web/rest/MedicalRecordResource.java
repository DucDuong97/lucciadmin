package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.MedicalRecord;
import com.lucci.webadmin.service.MedicalRecordService;
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
 * REST controller for managing {@link com.lucci.webadmin.domain.MedicalRecord}.
 */
@RestController
@RequestMapping("/api")
public class MedicalRecordResource {

    private final Logger log = LoggerFactory.getLogger(MedicalRecordResource.class);

    private static final String ENTITY_NAME = "medicalRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordResource(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * {@code POST  /medical-records} : Create a new medicalRecord.
     *
     * @param medicalRecord the medicalRecord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicalRecord, or with status {@code 400 (Bad Request)} if the medicalRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medical-records")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws URISyntaxException {
        log.debug("REST request to save MedicalRecord : {}", medicalRecord);
        if (medicalRecord.getId() != null) {
            throw new BadRequestAlertException("A new medicalRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalRecord result = medicalRecordService.save(medicalRecord);
        return ResponseEntity.created(new URI("/api/medical-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medical-records} : Updates an existing medicalRecord.
     *
     * @param medicalRecord the medicalRecord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicalRecord,
     * or with status {@code 400 (Bad Request)} if the medicalRecord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicalRecord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medical-records")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws URISyntaxException {
        log.debug("REST request to update MedicalRecord : {}", medicalRecord);
        if (medicalRecord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalRecord result = medicalRecordService.save(medicalRecord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicalRecord.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medical-records} : get all the medicalRecords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicalRecords in body.
     */
    @GetMapping("/medical-records")
    public List<MedicalRecord> getAllMedicalRecords() {
        log.debug("REST request to get all MedicalRecords");
        return medicalRecordService.findAll();
    }

    /**
     * {@code GET  /medical-records/:id} : get the "id" medicalRecord.
     *
     * @param id the id of the medicalRecord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicalRecord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medical-records/{id}")
    public ResponseEntity<MedicalRecord> getMedicalRecord(@PathVariable Long id) {
        log.debug("REST request to get MedicalRecord : {}", id);
        Optional<MedicalRecord> medicalRecord = medicalRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalRecord);
    }

    /**
     * {@code DELETE  /medical-records/:id} : delete the "id" medicalRecord.
     *
     * @param id the id of the medicalRecord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medical-records/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        log.debug("REST request to delete MedicalRecord : {}", id);
        medicalRecordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
