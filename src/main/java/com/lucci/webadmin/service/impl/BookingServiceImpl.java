package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.BookingService;
import com.lucci.webadmin.domain.Booking;
import com.lucci.webadmin.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Booking}.
 */
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking save(Booking booking) {
        log.debug("Request to save Booking : {}", booking);
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findAll() {
        log.debug("Request to get all Bookings");
        return bookingRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> findOne(Long id) {
        log.debug("Request to get Booking : {}", id);
        return bookingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Booking : {}", id);
        bookingRepository.deleteById(id);
    }

  @Override
  public List<Booking> findByPatientId(Long patientId) {
    return bookingRepository.findByPatientId(patientId);
  }
}
