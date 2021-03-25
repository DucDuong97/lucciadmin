package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Receptionist;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Receptionist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceptionistRepository extends JpaRepository<Receptionist, Long> {
}
