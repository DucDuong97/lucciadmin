package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Branch;
import com.lucci.webadmin.domain.Employee;

import com.lucci.webadmin.domain.enumeration.EmployeeRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUserLogin(String login);

    Page<Employee> findByWorkAtAndIdNot(Branch branch, Long managerId, Pageable pageable);

    List<Employee> findByWorkAtAndRole(Branch branch, EmployeeRole role);

    List<Employee> findByRole(EmployeeRole role);
}
