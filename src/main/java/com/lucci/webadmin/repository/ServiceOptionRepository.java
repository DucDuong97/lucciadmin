package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.ServiceOption;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOptionRepository extends JpaRepository<ServiceOption, Long> {
}
