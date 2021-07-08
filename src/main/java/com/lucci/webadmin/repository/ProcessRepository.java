package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Process;

import com.lucci.webadmin.repository.custom.CustomProcessRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Process entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessRepository extends JpaRepository<Process, Long>, CustomProcessRepository {
}
