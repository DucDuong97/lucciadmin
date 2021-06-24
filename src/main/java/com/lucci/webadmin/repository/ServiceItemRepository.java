package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.ServiceItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ServiceItem entity.
 */
@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {

    @Query(value = "select distinct serviceItem from ServiceItem serviceItem left join fetch serviceItem.customerImgUrls",
        countQuery = "select count(distinct serviceItem) from ServiceItem serviceItem")
    Page<ServiceItem> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct serviceItem from ServiceItem serviceItem left join fetch serviceItem.customerImgUrls")
    List<ServiceItem> findAllWithEagerRelationships();

    @Query("select serviceItem from ServiceItem serviceItem left join fetch serviceItem.customerImgUrls where serviceItem.id =:id")
    Optional<ServiceItem> findOneWithEagerRelationships(@Param("id") Long id);
}
