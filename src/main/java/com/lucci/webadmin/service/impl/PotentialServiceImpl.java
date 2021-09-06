package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.ConsultService;
import com.lucci.webadmin.service.CustomerService;
import com.lucci.webadmin.service.PotentialService;
import com.lucci.webadmin.domain.Potential;
import com.lucci.webadmin.repository.PotentialRepository;
import com.lucci.webadmin.service.dto.ConsultDTO;
import com.lucci.webadmin.service.dto.CustomerDTO;
import com.lucci.webadmin.service.dto.PotentialDTO;
import com.lucci.webadmin.service.mapper.PotentialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Potential}.
 */
@Service
@Transactional
public class PotentialServiceImpl implements PotentialService {

    private final Logger log = LoggerFactory.getLogger(PotentialServiceImpl.class);

    private final PotentialRepository potentialRepository;

    private final PotentialMapper potentialMapper;

    private final CustomerService customerService;
    private final ConsultService consultService;

    public PotentialServiceImpl(PotentialRepository potentialRepository, PotentialMapper potentialMapper, CustomerService customerService, ConsultService consultService) {
        this.potentialRepository = potentialRepository;
        this.potentialMapper = potentialMapper;
        this.customerService = customerService;
        this.consultService = consultService;
    }

    @Override
    public PotentialDTO save(PotentialDTO potentialDTO) {
        log.debug("Request to save Potential : {}", potentialDTO);
        if (potentialDTO.getId() == null) {
            CustomerDTO newCustomerDTO = new CustomerDTO();
            newCustomerDTO.setName(potentialDTO.getName());
            newCustomerDTO.setPhone(potentialDTO.getPhone());
            newCustomerDTO.setGender(potentialDTO.getGender());
            newCustomerDTO.setBirth(LocalDate.now());
            newCustomerDTO.setNewCustomer(true);
            newCustomerDTO = customerService.save(newCustomerDTO);

            ConsultDTO consultDTO = new ConsultDTO();
            consultDTO.setCustomerId(newCustomerDTO.getId());
            consultDTO.setBranchId(potentialDTO.getBranchId());
        }
        Potential potential = potentialMapper.toEntity(potentialDTO);
        potential = potentialRepository.save(potential);
        return potentialMapper.toDto(potential);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PotentialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Potentials");
        return potentialRepository.findAll(pageable)
            .map(potentialMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PotentialDTO> findOne(Long id) {
        log.debug("Request to get Potential : {}", id);
        return potentialRepository.findById(id)
            .map(potentialMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Potential : {}", id);
        potentialRepository.deleteById(id);
    }
}
