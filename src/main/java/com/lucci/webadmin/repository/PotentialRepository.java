package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Potential;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Potential entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PotentialRepository extends JpaRepository<Potential, Long> {
}
