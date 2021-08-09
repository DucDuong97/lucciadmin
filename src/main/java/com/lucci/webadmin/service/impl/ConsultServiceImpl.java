package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.domain.Booking;
import com.lucci.webadmin.domain.PricingCard;
import com.lucci.webadmin.service.ConsultService;
import com.lucci.webadmin.domain.Consult;
import com.lucci.webadmin.repository.ConsultRepository;
import com.lucci.webadmin.service.TreatmentPlanService;
import com.lucci.webadmin.service.dto.ConsultDTO;
import com.lucci.webadmin.service.dto.TreatmentDTO;
import com.lucci.webadmin.service.dto.TreatmentPlanDTO;
import com.lucci.webadmin.service.mapper.ConsultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static com.lucci.webadmin.domain.enumeration.BookingState.*;

/**
 * Service Implementation for managing {@link Consult}.
 */
@Service
@Transactional
public class ConsultServiceImpl implements ConsultService {

    private final Logger log = LoggerFactory.getLogger(ConsultServiceImpl.class);

    private final ConsultRepository consultRepository;

    private final ConsultMapper consultMapper;

    private final TreatmentPlanService planService;

    public ConsultServiceImpl(ConsultRepository consultRepository, ConsultMapper consultMapper, TreatmentPlanService planService) {
        this.consultRepository = consultRepository;
        this.consultMapper = consultMapper;
        this.planService = planService;
    }

    @Override
    public ConsultDTO save(ConsultDTO consultDTO) {
        log.debug("Request to save Consult : {}", consultDTO);
        Consult consult = consultMapper.toEntity(consultDTO);
        consult.setState(COMING);
        consult = consultRepository.save(consult);
        return consultMapper.toDto(consult);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consults");
        return consultRepository.findAllWithAuthority(pageable)
            .map(consultMapper::toDto);
    }


    public Page<ConsultDTO> findAllWithEagerRelationships(Pageable pageable) {
        return consultRepository.findAllWithEagerRelationships(pageable).map(consultMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConsultDTO> findOne(Long id) {
        log.debug("Request to get Consult : {}", id);
        return consultRepository.findOneWithEagerRelationships(id)
            .map(consultMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consult : {}", id);
        consultRepository.deleteById(id);
    }


    @Override
    public void came(Long id) {
        Consult consult = consultRepository.findById(id).get();
        consult.setState(CAME);
        createNewTreatmentPlan(consult);
    }

    private void createNewTreatmentPlan(Consult consult) {
        TreatmentPlanDTO newPlan = new TreatmentPlanDTO();
        for (PricingCard service : consult.getServices()) {
            newPlan.setCustomerId(consult.getCustomer().getId());
            newPlan.setServiceId(service.getId());
            planService.save(newPlan);
        }
    }

    @Override
    public void notCame(Long id) {
        Consult con = consultRepository.findById(id).get();
        con.setState(NOT_CAME);
    }
}
