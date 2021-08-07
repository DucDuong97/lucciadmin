package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.ProcessService;
import com.lucci.webadmin.domain.Process;
import com.lucci.webadmin.repository.ProcessRepository;
import com.lucci.webadmin.service.dto.ProcessDTO;
import com.lucci.webadmin.service.mapper.ProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Process}.
 */
@Service
@Transactional
public class ProcessServiceImpl implements ProcessService {

    private final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);

    private final ProcessRepository processRepository;

    private final ProcessMapper processMapper;

    public ProcessServiceImpl(ProcessRepository processRepository, ProcessMapper processMapper) {
        this.processRepository = processRepository;
        this.processMapper = processMapper;
    }

    @Override
    public ProcessDTO save(ProcessDTO processDTO) {
        log.debug("Request to save Process : {}", processDTO);
        Process process = processMapper.toEntity(processDTO);
        process = processRepository.save(process);
        return processMapper.toDto(process);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessDTO> findAll() {
        log.debug("Request to get all Processes");
        return processRepository.findAll().stream()
            .map(processMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessDTO> findOne(Long id) {
        log.debug("Request to get Process : {}", id);
        return processRepository.findById(id)
            .map(processMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Process : {}", id);
        processRepository.deleteById(id);
    }
}
