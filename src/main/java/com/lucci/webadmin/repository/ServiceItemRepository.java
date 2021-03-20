package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.ServiceItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
}
