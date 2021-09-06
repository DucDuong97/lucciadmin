package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.service.PotentialService;
import com.lucci.webadmin.web.rest.errors.BadRequestAlertException;
import com.lucci.webadmin.service.dto.PotentialDTO;

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
 * REST controller for managing {@link com.lucci.webadmin.domain.Potential}.
 */
@RestController
@RequestMapping("/api")
public class PotentialResource {

    private final Logger log = LoggerFactory.getLogger(PotentialResource.class);

    private static final String ENTITY_NAME = "potential";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PotentialService potentialService;

    public PotentialResource(PotentialService potentialService) {
        this.potentialService = potentialService;
    }

    /**
     * {@code POST  /potentials} : Create a new potential.
     *
     * @param potentialDTO the potentialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new potentialDTO, or with status {@code 400 (Bad Request)} if the potential has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/potentials")
    public ResponseEntity<PotentialDTO> createPotential(@Valid @RequestBody PotentialDTO potentialDTO) throws URISyntaxException {
        log.debug("REST request to save Potential : {}", potentialDTO);
        if (potentialDTO.getId() != null) {
            throw new BadRequestAlertException("A new potential cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PotentialDTO result = potentialService.save(potentialDTO);
        return ResponseEntity.created(new URI("/api/potentials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /potentials} : Updates an existing potential.
     *
     * @param potentialDTO the potentialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated potentialDTO,
     * or with status {@code 400 (Bad Request)} if the potentialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the potentialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/potentials")
    public ResponseEntity<PotentialDTO> updatePotential(@Valid @RequestBody PotentialDTO potentialDTO) throws URISyntaxException {
        log.debug("REST request to update Potential : {}", potentialDTO);
        if (potentialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PotentialDTO result = potentialService.save(potentialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, potentialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /potentials} : get all the potentials.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of potentials in body.
     */
    @GetMapping("/potentials")
    public ResponseEntity<List<PotentialDTO>> getAllPotentials(Pageable pageable) {
        log.debug("REST request to get a page of Potentials");
        Page<PotentialDTO> page = potentialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /potentials/:id} : get the "id" potential.
     *
     * @param id the id of the potentialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the potentialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/potentials/{id}")
    public ResponseEntity<PotentialDTO> getPotential(@PathVariable Long id) {
        log.debug("REST request to get Potential : {}", id);
        Optional<PotentialDTO> potentialDTO = potentialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(potentialDTO);
    }

    /**
     * {@code DELETE  /potentials/:id} : delete the "id" potential.
     *
     * @param id the id of the potentialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/potentials/{id}")
    public ResponseEntity<Void> deletePotential(@PathVariable Long id) {
        log.debug("REST request to delete Potential : {}", id);
        potentialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
