package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.ServiceOption;
import com.lucci.webadmin.service.ServiceOptionService;
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
 * REST controller for managing {@link com.lucci.webadmin.domain.ServiceOption}.
 */
@RestController
@RequestMapping("/api")
public class ServiceOptionResource {

    private final Logger log = LoggerFactory.getLogger(ServiceOptionResource.class);

    private static final String ENTITY_NAME = "serviceOption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOptionService serviceOptionService;

    public ServiceOptionResource(ServiceOptionService serviceOptionService) {
        this.serviceOptionService = serviceOptionService;
    }

    /**
     * {@code POST  /service-options} : Create a new serviceOption.
     *
     * @param serviceOption the serviceOption to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOption, or with status {@code 400 (Bad Request)} if the serviceOption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-options")
    public ResponseEntity<ServiceOption> createServiceOption(@Valid @RequestBody ServiceOption serviceOption) throws URISyntaxException {
        log.debug("REST request to save ServiceOption : {}", serviceOption);
        if (serviceOption.getId() != null) {
            throw new BadRequestAlertException("A new serviceOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceOption result = serviceOptionService.save(serviceOption);
        return ResponseEntity.created(new URI("/api/service-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-options} : Updates an existing serviceOption.
     *
     * @param serviceOption the serviceOption to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOption,
     * or with status {@code 400 (Bad Request)} if the serviceOption is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOption couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-options")
    public ResponseEntity<ServiceOption> updateServiceOption(@Valid @RequestBody ServiceOption serviceOption) throws URISyntaxException {
        log.debug("REST request to update ServiceOption : {}", serviceOption);
        if (serviceOption.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceOption result = serviceOptionService.save(serviceOption);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceOption.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-options} : get all the serviceOptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOptions in body.
     */
    @CrossOrigin
    @GetMapping("/service-options")
    public List<ServiceOption> getAllServiceOptions() {
        log.debug("REST request to get all ServiceOptions");
        return serviceOptionService.findAll();
    }

    /**
     * {@code GET  /service-options/:id} : get the "id" serviceOption.
     *
     * @param id the id of the serviceOption to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOption, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/service-options/{id}")
    public ResponseEntity<ServiceOption> getServiceOption(@PathVariable Long id) {
        log.debug("REST request to get ServiceOption : {}", id);
        Optional<ServiceOption> serviceOption = serviceOptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOption);
    }

    /**
     * {@code DELETE  /service-options/:id} : delete the "id" serviceOption.
     *
     * @param id the id of the serviceOption to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-options/{id}")
    public ResponseEntity<Void> deleteServiceOption(@PathVariable Long id) {
        log.debug("REST request to delete ServiceOption : {}", id);
        serviceOptionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
