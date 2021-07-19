package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.TreatmentPlan;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TreatmentPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Long> {
}
