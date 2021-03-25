package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.TreatmentHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TreatmentHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TreatmentHistoryRepository extends JpaRepository<TreatmentHistory, Long> {
}
