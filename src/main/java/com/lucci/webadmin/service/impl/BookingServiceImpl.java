package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.BookingService;
import com.lucci.webadmin.domain.Booking;
import com.lucci.webadmin.repository.BookingRepository;
import com.lucci.webadmin.service.TreatmentService;
import com.lucci.webadmin.service.dto.BookingDTO;
import com.lucci.webadmin.service.dto.TreatmentDTO;
import com.lucci.webadmin.service.mapper.BookingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static com.lucci.webadmin.domain.enumeration.BookingState.*;

/**
 * Service Implementation for managing {@link Booking}.
 */
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final TreatmentService treatmentService;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper, @Lazy TreatmentService treatmentService) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.treatmentService = treatmentService;
    }

    @Override
    public BookingDTO save(BookingDTO bookingDTO) {
        log.debug("Request to save Booking : {}", bookingDTO);
        Booking booking = bookingMapper.toEntity(bookingDTO);
        handleState(booking);
        booking = bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    private void handleState(Booking booking) {
        booking.setState(COMING);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bookings");
        return bookingRepository.findByState(COMING, pageable)
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

    @Override
    public void came(Long id) {
        Booking booking = bookingRepository.findById(id).get();
        booking.setState(CAME);
        createNewTreatment(booking);
    }

    private void createNewTreatment(Booking booking) {
        TreatmentDTO newTreatment = new TreatmentDTO();
        newTreatment.setTreatmentPlanId(booking.getTreatmentPlan().getId());
        newTreatment.setDate(LocalDate.now());
        newTreatment.setDoctorId(booking.getCorrespondDoctor().getId());
        treatmentService.save(newTreatment);
    }

    @Override
    public void notCame(Long id) {
        Booking booking = bookingRepository.findById(id).get();
        booking.setState(NOT_CAME);
    }

    @Override
    public BookingDTO assignDoctor(BookingDTO bookingDTO) {
        log.debug("Request to assign Doctor to Booking: {}", bookingDTO);
        Booking request = bookingMapper.toEntity(bookingDTO);
        Booking booking = bookingRepository.findById(bookingDTO.getId()).get();
        booking.setCorrespondDoctor(request.getCorrespondDoctor());
        return bookingMapper.toDto(booking);
    }
}
