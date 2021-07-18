package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.BookingService;
import com.lucci.webadmin.domain.Booking;
import com.lucci.webadmin.repository.BookingRepository;
import com.lucci.webadmin.service.dto.BookingDTO;
import com.lucci.webadmin.service.mapper.BookingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Booking}.
 */
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingDTO save(BookingDTO bookingDTO) {
        log.debug("Request to save Booking : {}", bookingDTO);
        Booking booking = bookingMapper.toEntity(bookingDTO);
        booking = bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bookings");
        return bookingRepository.findAllWithAuthority(pageable)
            .map(bookingMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BookingDTO> findOne(Long id) {
        log.debug("Request to get Booking : {}", id);
        return bookingRepository.findById(id)
            .map(bookingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Booking : {}", id);
        bookingRepository.deleteById(id);
    }

//    @Override
//    public Page<BookingDTO> findWithEmployee(Pageable pageable) {
//
//        Employee employee = SecurityUtils.getCurrentUserLogin()
//            .flatMap(employeeRepository::findByUsersLogin)
//            .orElse(null);
//
//        else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.BRANCH_BOSS_DOCTOR)) {
//            assert employee != null;
//            return bookingRepository.findByBranch(employee.getWorkAt(), pageable)
//                .map(bookingMapper::toDto);
//        }
//        else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DOCTOR)) {
//            return bookingRepository.findByCorrespondDoctor(employee, pageable)
//                .map(bookingMapper::toDto);
//        }
//        else {
//            return Page.empty();
//        }
//    }
}
