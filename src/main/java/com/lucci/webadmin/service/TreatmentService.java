package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.Treatment;
import com.lucci.webadmin.domain.enumeration.TreatmentState;
import com.lucci.webadmin.repository.TreatmentRepository;
import com.lucci.webadmin.service.dto.BookingDTO;
import com.lucci.webadmin.service.dto.TreatmentDTO;
import com.lucci.webadmin.service.dto.TreatmentPlanDTO;
import com.lucci.webadmin.service.mapper.TreatmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.lucci.webadmin.domain.enumeration.TreatmentState.FINISH;
import static com.lucci.webadmin.domain.enumeration.TreatmentState.IN_PROCESS;

/**
 * Service Implementation for managing {@link Treatment}.
 */
@Service
@Transactional
public class TreatmentService {

    private final Logger log = LoggerFactory.getLogger(TreatmentService.class);

    private BookingService bookingService;
    private TreatmentPlanService treatmentPlanService;

    private final TreatmentRepository treatmentRepository;

    private final TreatmentMapper treatmentMapper;

    public TreatmentService(@Lazy BookingService bookingService, TreatmentPlanService treatmentPlanService, TreatmentRepository treatmentRepository, TreatmentMapper treatmentMapper) {
        this.bookingService = bookingService;
        this.treatmentPlanService = treatmentPlanService;
        this.treatmentRepository = treatmentRepository;
        this.treatmentMapper = treatmentMapper;
    }

    /**
     * Save a treatment.
     *
     * @param treatmentDTO the entity to save.
     * @return the persisted entity.
     */
    public TreatmentDTO save(TreatmentDTO treatmentDTO) {
        log.debug("Request to save Treatment : {}", treatmentDTO);
        Treatment treatment = treatmentMapper.toEntity(treatmentDTO);
        if (treatment.getId() == null) {
            treatment.setState(IN_PROCESS);
            List<Treatment> oldTreatments = treatmentRepository.findByTreatmentPlanId(treatmentDTO.getTreatmentPlanId());
            if (oldTreatments.size() > 0) {
                Treatment lastTreatment = oldTreatments.stream()
                    .max(Comparator.comparing(Treatment::getDate))
                    .get();
                treatment.setDescription(lastTreatment.getNextPlan());
            }
        }
        else {
            TreatmentState state = treatmentRepository.findById(treatment.getId()).get().getState();
            if (state.equals(IN_PROCESS)) {
                treatment.setState(FINISH);
                createNewBooking(treatment);
            } else if (state.equals(FINISH)) {
                throw new EntityAlreadyFinishedException();
            }
        }
        Treatment savedTreatment = treatmentRepository.save(treatment);
        return treatmentMapper.toDto(savedTreatment);
    }

    private void createNewBooking(Treatment treatment) {
        if (treatment.getRevisitDate() != null) {
            BookingDTO revisitBooking = new BookingDTO();
            revisitBooking.setDate(treatment.getRevisitDate());
            revisitBooking.setTime(LocalTime.MIDNIGHT);
            revisitBooking.setTreatmentPlanId(treatment.getTreatmentPlan().getId());
            TreatmentPlanDTO treatmentPlan = treatmentPlanService.findOne(treatment.getTreatmentPlan().getId()).get();
            revisitBooking.setCustomerId(treatmentPlan.getCustomerId());
            bookingService.save(revisitBooking);
        }
    }

    /**
     * Get all the treatments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TreatmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Treatments");
        return treatmentRepository.findAll(pageable)
            .map(treatmentMapper::toDto);
    }


    /**
     * Get all the treatments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TreatmentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return treatmentRepository.findAllWithEagerRelationships(pageable).map(treatmentMapper::toDto);
    }

    /**
     * Get one treatment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TreatmentDTO> findOne(Long id) {
        log.debug("Request to get Treatment : {}", id);
        return treatmentRepository.findOneWithEagerRelationships(id)
            .map(treatmentMapper::toDto);
    }

    /**
     * Delete the treatment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Treatment : {}", id);
        treatmentRepository.deleteById(id);
    }

    public Page<TreatmentDTO> findByPlanId(Pageable pageable, Long planId) {
        return treatmentRepository.findByTreatmentPlanId(pageable, planId).map(treatmentMapper::toDto);
    }
}
