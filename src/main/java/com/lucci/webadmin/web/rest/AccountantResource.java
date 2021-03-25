package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.Accountant;
import com.lucci.webadmin.service.AccountantService;
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
 * REST controller for managing {@link com.lucci.webadmin.domain.Accountant}.
 */
@RestController
@RequestMapping("/api")
public class AccountantResource {

    private final Logger log = LoggerFactory.getLogger(AccountantResource.class);

    private static final String ENTITY_NAME = "accountant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountantService accountantService;

    public AccountantResource(AccountantService accountantService) {
        this.accountantService = accountantService;
    }

    /**
     * {@code POST  /accountants} : Create a new accountant.
     *
     * @param accountant the accountant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountant, or with status {@code 400 (Bad Request)} if the accountant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accountants")
    public ResponseEntity<Accountant> createAccountant(@RequestBody Accountant accountant) throws URISyntaxException {
        log.debug("REST request to save Accountant : {}", accountant);
        if (accountant.getId() != null) {
            throw new BadRequestAlertException("A new accountant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Accountant result = accountantService.save(accountant);
        return ResponseEntity.created(new URI("/api/accountants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accountants} : Updates an existing accountant.
     *
     * @param accountant the accountant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountant,
     * or with status {@code 400 (Bad Request)} if the accountant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accountants")
    public ResponseEntity<Accountant> updateAccountant(@RequestBody Accountant accountant) throws URISyntaxException {
        log.debug("REST request to update Accountant : {}", accountant);
        if (accountant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Accountant result = accountantService.save(accountant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountant.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /accountants} : get all the accountants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountants in body.
     */
    @GetMapping("/accountants")
    public List<Accountant> getAllAccountants() {
        log.debug("REST request to get all Accountants");
        return accountantService.findAll();
    }

    /**
     * {@code GET  /accountants/:id} : get the "id" accountant.
     *
     * @param id the id of the accountant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accountants/{id}")
    public ResponseEntity<Accountant> getAccountant(@PathVariable Long id) {
        log.debug("REST request to get Accountant : {}", id);
        Optional<Accountant> accountant = accountantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountant);
    }

    /**
     * {@code DELETE  /accountants/:id} : delete the "id" accountant.
     *
     * @param id the id of the accountant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accountants/{id}")
    public ResponseEntity<Void> deleteAccountant(@PathVariable Long id) {
        log.debug("REST request to delete Accountant : {}", id);
        accountantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
