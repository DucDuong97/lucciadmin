package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.Blog;
import com.lucci.webadmin.domain.CustomerReview;
import com.lucci.webadmin.domain.ImgUrl;
import com.lucci.webadmin.service.CustomerReviewService;
import com.lucci.webadmin.service.ImgUrlService;
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
 * REST controller for managing {@link com.lucci.webadmin.domain.CustomerReview}.
 */
@RestController
@RequestMapping("/api")
public class CustomerReviewResource {

    private final Logger log = LoggerFactory.getLogger(CustomerReviewResource.class);

    private static final String ENTITY_NAME = "customerReview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerReviewService customerReviewService;
    private final ImgUrlService imgUrlService;

    public CustomerReviewResource(CustomerReviewService customerReviewService, ImgUrlService imgUrlService) {
        this.customerReviewService = customerReviewService;
        this.imgUrlService = imgUrlService;
    }

    /**
     * {@code POST  /customer-reviews} : Create a new customerReview.
     *
     * @param customerReview the customerReview to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerReview, or with status {@code 400 (Bad Request)} if the customerReview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-reviews")
    public ResponseEntity<CustomerReview> createCustomerReview(@Valid @RequestBody CustomerReview customerReview) throws URISyntaxException {
        log.debug("REST request to save CustomerReview : {}", customerReview);
        if (customerReview.getId() != null) {
            throw new BadRequestAlertException("A new customerReview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerReview result = customerReviewService.save(customerReview);
        return ResponseEntity.created(new URI("/api/customer-reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-reviews} : Updates an existing customerReview.
     *
     * @param customerReview the customerReview to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerReview,
     * or with status {@code 400 (Bad Request)} if the customerReview is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerReview couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-reviews")
    public ResponseEntity<CustomerReview> updateCustomerReview(@Valid @RequestBody CustomerReview customerReview) throws URISyntaxException {
        log.debug("REST request to update CustomerReview : {}", customerReview);
        if (customerReview.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<CustomerReview> crOpt = customerReviewService.findOne(customerReview.getId());
        if (!crOpt.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idinvalid");
        }
        CustomerReview result = customerReviewService.save(customerReview);
        ImgUrl oldImgUrl = crOpt.get().getCustomerImgUrl();
        if (oldImgUrl != null && !customerReview.getCustomerImgUrl().equals(oldImgUrl)) {
            imgUrlService.delete(oldImgUrl.getId());
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerReview.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-reviews} : get all the customerReviews.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerReviews in body.
     */
    @CrossOrigin
    @GetMapping("/customer-reviews")
    public List<CustomerReview> getAllCustomerReviews() {
        log.debug("REST request to get all CustomerReviews");
        return customerReviewService.findAll();
    }

    /**
     * {@code GET  /customer-reviews/:id} : get the "id" customerReview.
     *
     * @param id the id of the customerReview to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerReview, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/customer-reviews/{id}")
    public ResponseEntity<CustomerReview> getCustomerReview(@PathVariable Long id) {
        log.debug("REST request to get CustomerReview : {}", id);
        Optional<CustomerReview> customerReview = customerReviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerReview);
    }

    /**
     * {@code DELETE  /customer-reviews/:id} : delete the "id" customerReview.
     *
     * @param id the id of the customerReview to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-reviews/{id}")
    public ResponseEntity<Void> deleteCustomerReview(@PathVariable Long id) {
        log.debug("REST request to delete CustomerReview : {}", id);
        Optional<CustomerReview> crOpt = customerReviewService.findOne(id);
        if (!crOpt.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idinvalid");
        }
        customerReviewService.delete(id);
        ImgUrl imgUrl = crOpt.get().getCustomerImgUrl();
        if (imgUrl != null) {
            imgUrlService.delete(imgUrl.getId());
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
