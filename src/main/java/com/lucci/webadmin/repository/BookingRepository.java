package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Booking;

import com.lucci.webadmin.domain.Branch;
import com.lucci.webadmin.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;

/**
 * Spring Data  repository for the Booking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findByBranch(Branch workAt, Pageable pageable);

    Page<Booking> findByCorrespondDoctor(Employee employee, Pageable pageable);
}
