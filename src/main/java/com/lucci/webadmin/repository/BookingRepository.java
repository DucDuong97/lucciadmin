package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Booking;

import com.lucci.webadmin.domain.Branch;
import com.lucci.webadmin.domain.Employee;
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

    Page<Booking> findByCorrespondDoctor(Employee employee, Pageable pageable);

    @Query(
        "select booking " +
        "from Booking booking " +
        "where " +
            "(true= ?#{hasRole('DOCTOR')} " +
                "and booking.correspondDoctor.user.login = ?#{principal.username}) or  " +
            "(true= ?#{hasRole('BRANCH_BOSS_DOCTOR')} " +
                "and booking.branch.id = ?#{@userService.getUserBranchId()}) or  " +
            "true= ?#{hasAnyRole('RECEPTIONIST', 'ADMIN', 'OPERATIONS_DIRECTOR')}"
    )
    Page<Booking> findAllWithAuthority(Pageable pageable);
}
