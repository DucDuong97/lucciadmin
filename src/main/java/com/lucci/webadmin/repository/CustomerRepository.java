package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select customer from Customer customer where customer.createdBy = ?#{principal.username} " +
                "or true= ?#{hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')}")
    Page<Customer> findAllWithAuthorization(Pageable pageable);
}
