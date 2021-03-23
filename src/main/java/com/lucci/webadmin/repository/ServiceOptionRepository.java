package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.ServiceOption;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the ServiceOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOptionRepository
    extends JpaRepository<ServiceOption, Long>, CustomServiceOptionRepository {
}
