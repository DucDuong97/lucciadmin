package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.TreatmentPlan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TreatmentPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Long> {

    Page<TreatmentPlan> findByCustomerId(Pageable pageable, Long customerId);
}
