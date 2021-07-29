package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Employee;

import com.lucci.webadmin.domain.enumeration.EmployeeRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByWorkAtIdAndRole(Long branchId, EmployeeRole role, Pageable pageable);
}
