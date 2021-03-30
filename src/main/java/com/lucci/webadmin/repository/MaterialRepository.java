package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Material;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Material entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
}