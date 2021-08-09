package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Consult;

import com.lucci.webadmin.domain.enumeration.BookingState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Consult entity.
 */
@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long> {

    @Query(value = "select distinct consult from Consult consult left join fetch consult.services",
        countQuery = "select count(distinct consult) from Consult consult")
    Page<Consult> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct consult from Consult consult left join fetch consult.services")
    List<Consult> findAllWithEagerRelationships();

    @Query("select consult from Consult consult left join fetch consult.services where consult.id =:id")
    Optional<Consult> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select consult from Consult consult left outer join consult.consultingDoctor" +
        " where state = 'COMING' AND" +
        " (consult.consultingDoctor.id = ?#{@userService.getRelatedEmployeeId()} or" +
        " true = ?#{hasRole('CONSULTANT')})"
    )
    Page<Consult> findAllWithAuthority(Pageable pageable);

    Page<Consult> findByState(BookingState state, Pageable pageable);

}
