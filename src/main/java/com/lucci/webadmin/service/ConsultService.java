package com.lucci.webadmin.service;

import com.lucci.webadmin.domain.Consult;
import com.lucci.webadmin.repository.ConsultRepository;
import com.lucci.webadmin.service.dto.ConsultDTO;
import com.lucci.webadmin.service.mapper.ConsultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Consult}.
 */
@Service
@Transactional
public class ConsultService {

    private final Logger log = LoggerFactory.getLogger(ConsultService.class);

    private final ConsultRepository consultRepository;

    private final ConsultMapper consultMapper;

    public ConsultService(ConsultRepository consultRepository, ConsultMapper consultMapper) {
        this.consultRepository = consultRepository;
        this.consultMapper = consultMapper;
    }

    /**
     * Save a consult.
     *
     * @param consultDTO the entity to save.
     * @return the persisted entity.
     */
    public ConsultDTO save(ConsultDTO consultDTO) {
        log.debug("Request to save Consult : {}", consultDTO);
        Consult consult = consultMapper.toEntity(consultDTO);
        consult = consultRepository.save(consult);
        return consultMapper.toDto(consult);
    }

    /**
     * Get all the consults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consults");
        return consultRepository.findAllWithAuthority(pageable)
            .map(consultMapper::toDto);
    }


    /**
     * Get all the consults with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ConsultDTO> findAllWithEagerRelationships(Pageable pageable) {
        return consultRepository.findAllWithEagerRelationships(pageable).map(consultMapper::toDto);
    }

    /**
     * Get one consult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConsultDTO> findOne(Long id) {
        log.debug("Request to get Consult : {}", id);
        return consultRepository.findOneWithEagerRelationships(id)
            .map(consultMapper::toDto);
    }

    /**
     * Delete the consult by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Consult : {}", id);
        consultRepository.deleteById(id);
    }
}
