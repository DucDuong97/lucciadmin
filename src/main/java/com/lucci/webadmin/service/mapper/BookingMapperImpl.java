package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.BookingDTO;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingMapperImpl implements BookingMapper {

    public List<Booking> toEntity(List<BookingDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Booking> list = new ArrayList<>( dtoList.size() );
        for ( BookingDTO bookingDTO : dtoList ) {
            list.add( toEntity( bookingDTO ) );
        }

        return list;
    }

    public List<BookingDTO> toDto(List<Booking> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BookingDTO> list = new ArrayList<>( entityList.size() );
        for ( Booking booking : entityList ) {
            list.add( toDto( booking ) );
        }

        return list;
    }

    public BookingDTO toDto(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setCorrespondDoctorId( bookingCorrespondDoctorId( booking ) );
        bookingDTO.setCustomerId( bookingCustomerId( booking ) );
        bookingDTO.setId( booking.getId() );
        bookingDTO.setDate( booking.getTime().toLocalDate() );
        bookingDTO.setTime( booking.getTime().toLocalTime() );
        bookingDTO.setBranch( booking.getBranch() );

        return bookingDTO;
    }

    public Booking toEntity(BookingDTO bookingDTO) {
        if ( bookingDTO == null ) {
            return null;
        }

        Booking booking = new Booking();

        booking.setCorrespondDoctor( idToDoctor( bookingDTO.getCorrespondDoctorId() ) );
        booking.setCustomer( idToCustomer( bookingDTO.getCustomerId() ) );
        booking.setId( bookingDTO.getId() );
        booking.setTime( ZonedDateTime.of(bookingDTO.getDate(), bookingDTO.getTime(), ZoneId.of("Asia/Ho_Chi_Minh")) );
        booking.setBranch( bookingDTO.getBranch() );

        return booking;
    }

    private Employee idToDoctor(Long id) {
        if (id == null) {
            return null;
        }
        Employee doctor = new Employee();
        doctor.setId(id);
        return doctor;
    }

    private Customer idToCustomer(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    public Booking fromId(Long id) {
        if (id == null) {
            return null;
        }
        Booking booking = new Booking();
        booking.setId(id);
        return booking;
    }

    private Long bookingCorrespondDoctorId(Booking booking) {
        if ( booking == null ) {
            return null;
        }
        Employee correspondDoctor = booking.getCorrespondDoctor();
        if ( correspondDoctor == null ) {
            return null;
        }
        Long id = correspondDoctor.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long bookingCustomerId(Booking booking) {
        if ( booking == null ) {
            return null;
        }
        Customer customer = booking.getCustomer();
        if ( customer == null ) {
            return null;
        }
        Long id = customer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
