package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.Policy;
import com.lucci.webadmin.service.PolicyService;
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
 * REST controller for managing {@link com.lucci.webadmin.domain.Policy}.
 */
@RestController
@RequestMapping("/api")
public class PolicyResource {

    private final Logger log = LoggerFactory.getLogger(PolicyResource.class);

    private static final String ENTITY_NAME = "policy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PolicyService policyService;

    public PolicyResource(PolicyService policyService) {
        this.policyService = policyService;
    }

    /**
     * {@code POST  /policies} : Create a new policy.
     *
     * @param policy the policy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new policy, or with status {@code 400 (Bad Request)} if the policy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/policies")
    public ResponseEntity<Policy> createPolicy(@Valid @RequestBody Policy policy) throws URISyntaxException {
        log.debug("REST request to save Policy : {}", policy);
        if (policy.getId() != null) {
            throw new BadRequestAlertException("A new policy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Policy result = policyService.save(policy);
        return ResponseEntity.created(new URI("/api/policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /policies} : Updates an existing policy.
     *
     * @param policy the policy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policy,
     * or with status {@code 400 (Bad Request)} if the policy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the policy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/policies")
    public ResponseEntity<Policy> updatePolicy(@Valid @RequestBody Policy policy) throws URISyntaxException {
        log.debug("REST request to update Policy : {}", policy);
        if (policy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Policy result = policyService.save(policy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, policy.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /policies} : get all the policies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of policies in body.
     */
    @CrossOrigin
    @GetMapping("/policies")
    public List<Policy> getAllPolicies() {
        log.debug("REST request to get all Policies");
        return policyService.findAll();
    }

    /**
     * {@code GET  /policies/:id} : get the "id" policy.
     *
     * @param id the id of the policy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the policy, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/policies/{id}")
    public ResponseEntity<Policy> getPolicy(@PathVariable Long id) {
        log.debug("REST request to get Policy : {}", id);
        Optional<Policy> policy = policyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(policy);
    }

    /**
     * {@code DELETE  /policies/:id} : delete the "id" policy.
     *
     * @param id the id of the policy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/policies/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        log.debug("REST request to delete Policy : {}", id);
        policyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
