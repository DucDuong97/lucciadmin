package com.lucci.webadmin.repository.custom;

import com.lucci.webadmin.domain.Process;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomProcessRepository {
    @Query(value = "SELECT * FROM process WHERE service_item_id=?1", nativeQuery = true)
    List<Process> findAllByServiceId(Long id);
}
