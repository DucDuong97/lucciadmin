package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Booking;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Booking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, CustomBookingRepository {
}
