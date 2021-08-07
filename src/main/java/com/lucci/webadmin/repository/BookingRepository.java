package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Booking;

import com.lucci.webadmin.domain.Branch;
import com.lucci.webadmin.domain.Employee;
import com.lucci.webadmin.domain.enumeration.BookingState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Booking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findByBranch(Branch workAt, Pageable pageable);

    Page<Booking> findByState(BookingState state, Pageable pageable);

    Page<Booking> findByCorrespondDoctor(Employee employee, Pageable pageable);

    @Query(
        "select booking " +
        "from Booking booking left outer join booking.correspondDoctor.user" +
        " where state = 'COMING' AND" +
            " (booking.correspondDoctor.user.login = ?#{principal.username} or" +
            " true= ?#{hasAnyRole('RECEPTIONIST', 'ADMIN', 'OPERATIONS_DIRECTOR')})"
    )
    Page<Booking> findAllWithAuthority(Pageable pageable);
}
