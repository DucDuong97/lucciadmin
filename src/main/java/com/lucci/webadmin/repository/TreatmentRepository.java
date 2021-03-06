package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Treatment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Treatment entity.
 */
@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    @Query(value = "select distinct treatment from Treatment treatment left join fetch treatment.treatmentImgUrls",
        countQuery = "select count(distinct treatment) from Treatment treatment")
    Page<Treatment> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct treatment from Treatment treatment left join fetch treatment.treatmentImgUrls")
    List<Treatment> findAllWithEagerRelationships();

    @Query("select treatment from Treatment treatment left join fetch treatment.treatmentImgUrls where treatment.id =:id")
    Optional<Treatment> findOneWithEagerRelationships(@Param("id") Long id);

    Page<Treatment> findByTreatmentPlanId(Pageable pageable, Long planId);

    List<Treatment> findByTreatmentPlanId(Long planId);
}
