package com.lucci.webadmin.repository.custom;

import com.lucci.webadmin.domain.ServiceOption;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomServiceOptionRepository {
    @Query(value = "SELECT * FROM service_option WHERE service_id=?1", nativeQuery = true)
    List<ServiceOption> findByServiceId(Long serviceId);
}
