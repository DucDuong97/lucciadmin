package com.lucci.webadmin.web.rest;

import com.lucci.webadmin.domain.Employee;
import com.lucci.webadmin.domain.enumeration.EmployeeRole;
import com.lucci.webadmin.repository.EmployeeRepository;
import com.lucci.webadmin.security.AuthoritiesConstants;
import com.lucci.webadmin.security.SecurityUtils;
import com.lucci.webadmin.web.rest.errors.BadRequestAlertException;

import com.lucci.webadmin.web.rest.errors.NoEmployeeForCurrentUserException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.lucci.webadmin.domain.enumeration.EmployeeRole.*;

/**
 * REST controller for managing {@link com.lucci.webadmin.domain.Employee}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmployeeResource {

    private static final List<EmployeeRole> FORBIDDEN_ROLES = Arrays.asList(ADMIN, OPERATIONS_DIRECTOR, MANAGER);

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "employee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeRepository employeeRepository;

    public EmployeeResource(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * {@code POST  /employees} : Create a new employee.
     *
     * @param employee the employee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employee, or with status {@code 400 (Bad Request)} if the employee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employee);
        if (employee.getId() != null) {
            throw new BadRequestAlertException("A new employee cannot already have an ID", ENTITY_NAME, "id_exists");
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER)) {
            if (FORBIDDEN_ROLES.contains(employee.getRole())) {
                throw new BadRequestAlertException("A Manager cannot add an Admin or Director", ENTITY_NAME, "forbidden_role");
            }
            SecurityUtils.getCurrentUserLogin()
                .flatMap(employeeRepository::findByUserLogin)
                .ifPresent(manager -> employee.setWorkAt(manager.getWorkAt()));
        }

        Employee result = employeeRepository.save(employee);
        return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employees} : Updates an existing employee.
     *
     * @param employee the employee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employee,
     * or with status {@code 400 (Bad Request)} if the employee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employees")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
        log.debug("REST request to update Employee : {}", employee);
        if (employee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Employee result = employeeRepository.save(employee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employee.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employees} : get all the employees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employees in body.
     */
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(Pageable pageable) {
        log.debug("REST request to get a page of Employees");
        Page<Employee> page;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER)) {
            Employee manager = SecurityUtils.getCurrentUserLogin()
                .flatMap(employeeRepository::findByUserLogin)
                .orElseThrow(NoEmployeeForCurrentUserException::new);
            page = employeeRepository.findByWorkAtAndIdNot(manager.getWorkAt(), manager.getId(), pageable);
        } else {
            page = employeeRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/employees/as-doctor-for-booking")
    public List<Employee> getAllDoctorsForBooking() {
        log.debug("REST request to get a list of doctors");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.OPERATIONS_DIRECTOR)) {
            return employeeRepository.findByRole(DOCTOR);
        }
        else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.BRANCH_BOSS_DOCTOR)) {
            Employee branchDoctor = SecurityUtils.getCurrentUserLogin()
                .flatMap(employeeRepository::findByUserLogin)
                .orElseThrow(NoEmployeeForCurrentUserException::new);
            return employeeRepository.findByWorkAtAndRole(branchDoctor.getWorkAt(), DOCTOR);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * {@code GET  /employees/:id} : get the "id" employee.
     *
     * @param id the id of the employee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);
        Optional<Employee> employee = employeeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(employee);
    }

    /**
     * {@code DELETE  /employees/:id} : delete the "id" employee.
     *
     * @param id the id of the employee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
