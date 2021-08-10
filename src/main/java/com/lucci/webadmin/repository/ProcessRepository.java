package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Process;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Process entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {

    List<Process> findByServiceItemId(Long serviceId);
}
