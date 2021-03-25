package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.MedicalRecord;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MedicalRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
}
