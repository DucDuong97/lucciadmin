package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.BookingDTO;

import org.mapstruct.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Mapper for the entity {@link Booking} and its DTO {@link BookingDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, CustomerMapper.class, TreatmentPlanMapper.class, BranchMapper.class})
public interface BookingMapper extends EntityMapper<BookingDTO, Booking> {

    @Mapping(source = "correspondDoctor.id", target = "correspondDoctorId")
    @Mapping(source = "correspondDoctor.name", target = "correspondDoctorName")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "treatmentPlan.id", target = "treatmentPlanId")
    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.adress", target = "branchAdress")
    @Mapping(source = "time", target = "date", qualifiedByName = "toLocalDate")
    @Mapping(source = "time", target = "time", qualifiedByName = "toLocalTime")
    BookingDTO toDto(Booking booking);

    @Mapping(source = "correspondDoctorId", target = "correspondDoctor")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "treatmentPlanId", target = "treatmentPlan")
    @Mapping(source = "branchId", target = "branch")
    @Mapping(source = "dateTime", target = "time")
    Booking toEntity(BookingDTO bookingDTO);

    @Named("toLocalTime")
    public static LocalTime toTime(LocalDateTime time) {
        return time.toLocalTime();
    }

    @Named("toLocalDate")
    public static LocalDate toDate(LocalDateTime time) {
        return time.toLocalDate();
    }

    default Booking fromId(Long id) {
        if (id == null) {
            return null;
        }
        Booking booking = new Booking();
        booking.setId(id);
        return booking;
    }
}
