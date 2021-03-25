package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.GetMaterial;
import com.lucci.webadmin.service.GetMaterialService;
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
 * REST controller for managing {@link com.lucci.webadmin.domain.GetMaterial}.
 */
@RestController
@RequestMapping("/api")
public class GetMaterialResource {

    private final Logger log = LoggerFactory.getLogger(GetMaterialResource.class);

    private static final String ENTITY_NAME = "getMaterial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GetMaterialService getMaterialService;

    public GetMaterialResource(GetMaterialService getMaterialService) {
        this.getMaterialService = getMaterialService;
    }

    /**
     * {@code POST  /get-materials} : Create a new getMaterial.
     *
     * @param getMaterial the getMaterial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new getMaterial, or with status {@code 400 (Bad Request)} if the getMaterial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/get-materials")
    public ResponseEntity<GetMaterial> createGetMaterial(@Valid @RequestBody GetMaterial getMaterial) throws URISyntaxException {
        log.debug("REST request to save GetMaterial : {}", getMaterial);
        if (getMaterial.getId() != null) {
            throw new BadRequestAlertException("A new getMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GetMaterial result = getMaterialService.save(getMaterial);
        return ResponseEntity.created(new URI("/api/get-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /get-materials} : Updates an existing getMaterial.
     *
     * @param getMaterial the getMaterial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated getMaterial,
     * or with status {@code 400 (Bad Request)} if the getMaterial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the getMaterial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/get-materials")
    public ResponseEntity<GetMaterial> updateGetMaterial(@Valid @RequestBody GetMaterial getMaterial) throws URISyntaxException {
        log.debug("REST request to update GetMaterial : {}", getMaterial);
        if (getMaterial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GetMaterial result = getMaterialService.save(getMaterial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, getMaterial.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /get-materials} : get all the getMaterials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of getMaterials in body.
     */
    @GetMapping("/get-materials")
    public List<GetMaterial> getAllGetMaterials() {
        log.debug("REST request to get all GetMaterials");
        return getMaterialService.findAll();
    }

    /**
     * {@code GET  /get-materials/:id} : get the "id" getMaterial.
     *
     * @param id the id of the getMaterial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the getMaterial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get-materials/{id}")
    public ResponseEntity<GetMaterial> getGetMaterial(@PathVariable Long id) {
        log.debug("REST request to get GetMaterial : {}", id);
        Optional<GetMaterial> getMaterial = getMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(getMaterial);
    }

    /**
     * {@code DELETE  /get-materials/:id} : delete the "id" getMaterial.
     *
     * @param id the id of the getMaterial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/get-materials/{id}")
    public ResponseEntity<Void> deleteGetMaterial(@PathVariable Long id) {
        log.debug("REST request to delete GetMaterial : {}", id);
        getMaterialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
