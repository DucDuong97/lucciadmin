package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Booking;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomBookingRepository {
  @Query(value = "SELECT * FROM booking WHERE patient_id=?1", nativeQuery = true)
    List<Booking> findByPatientId(Long patientId);
}
