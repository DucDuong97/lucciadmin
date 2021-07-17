package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select customer from Customer customer where customer.correspondConsultant.login = ?#{principal.username}")
    Page<Customer> findByCorrespondConsultantIsCurrentUser(Pageable pageable);
}
