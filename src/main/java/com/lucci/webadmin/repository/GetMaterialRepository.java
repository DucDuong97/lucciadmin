package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.GetMaterial;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GetMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GetMaterialRepository extends JpaRepository<GetMaterial, Long> {
}
