package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.SingletonContent;
import com.lucci.webadmin.service.SingletonContentService;
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
 * REST controller for managing {@link com.lucci.webadmin.domain.SingletonContent}.
 */
@RestController
@RequestMapping("/api")
public class SingletonContentResource {

    private final Logger log = LoggerFactory.getLogger(SingletonContentResource.class);

    private static final String ENTITY_NAME = "singletonContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SingletonContentService singletonContentService;

    public SingletonContentResource(SingletonContentService singletonContentService) {
        this.singletonContentService = singletonContentService;
    }

    /**
     * {@code POST  /singleton-contents} : Create a new singletonContent.
     *
     * @param singletonContent the singletonContent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new singletonContent, or with status {@code 400 (Bad Request)} if the singletonContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/singleton-contents")
    public ResponseEntity<SingletonContent> createSingletonContent(@Valid @RequestBody SingletonContent singletonContent) throws URISyntaxException {
        log.debug("REST request to save SingletonContent : {}", singletonContent);
        if (singletonContent.getId() != null) {
            throw new BadRequestAlertException("A new singletonContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SingletonContent result = singletonContentService.save(singletonContent);
        return ResponseEntity.created(new URI("/api/singleton-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /singleton-contents} : Updates an existing singletonContent.
     *
     * @param singletonContent the singletonContent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated singletonContent,
     * or with status {@code 400 (Bad Request)} if the singletonContent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the singletonContent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/singleton-contents")
    public ResponseEntity<SingletonContent> updateSingletonContent(@Valid @RequestBody SingletonContent singletonContent) throws URISyntaxException {
        log.debug("REST request to update SingletonContent : {}", singletonContent);
        if (singletonContent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SingletonContent result = singletonContentService.save(singletonContent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, singletonContent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /singleton-contents} : get all the singletonContents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of singletonContents in body.
     */
    @GetMapping("/singleton-contents")
    public List<SingletonContent> getAllSingletonContents() {
        log.debug("REST request to get all SingletonContents");
        return singletonContentService.findAll();
    }

    /**
     * {@code GET  /singleton-contents/:id} : get the "id" singletonContent.
     *
     * @param id the id of the singletonContent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the singletonContent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/singleton-contents/{id}")
    public ResponseEntity<SingletonContent> getSingletonContent(@PathVariable Long id) {
        log.debug("REST request to get SingletonContent : {}", id);
        Optional<SingletonContent> singletonContent = singletonContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(singletonContent);
    }

    /**
     * {@code DELETE  /singleton-contents/:id} : delete the "id" singletonContent.
     *
     * @param id the id of the singletonContent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/singleton-contents/{id}")
    public ResponseEntity<Void> deleteSingletonContent(@PathVariable Long id) {
        log.debug("REST request to delete SingletonContent : {}", id);
        singletonContentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
