package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Accountant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Accountant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountantRepository extends JpaRepository<Accountant, Long> {
}
