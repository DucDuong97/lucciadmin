package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.Customer;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * REST controller for actions of {@link com.lucci.webadmin.domain.Employee as consultant}.
 */
@RestController
@RequestMapping("/api/as-consultant")
@Secured("ROLE_CONSULTANT")
public class AsConsultantResource {
    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

//    @GetMapping("/customers")
//    public ResponseEntity<List<Customer>> getAllCustomersAsConsultant(Pageable pageable) {
//        log.debug("REST request to get a page of Customers");
//        Page<Customer> page = customerRepository.findByCreatedByIsCurrentUser(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }
}
