package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.domain.enumeration.EmployeeRole;
import com.lucci.webadmin.service.EmployeeService;
import com.lucci.webadmin.domain.Employee;
import com.lucci.webadmin.repository.EmployeeRepository;
import com.lucci.webadmin.service.dto.EmployeeDTO;
import com.lucci.webadmin.service.mapper.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.lucci.webadmin.domain.enumeration.EmployeeRole.*;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable)
            .map(employeeMapper::toDto);
    }

    @Override
    public Page<EmployeeDTO> findDoctorAtBranch(Pageable pageable, Long branchId) {
        log.debug("Request to get Doctors at Branch {}", branchId);
        return employeeRepository.findByWorkAtIdAndRole(branchId, DOCTOR, pageable)
            .map(employeeMapper::toDto);
    }

    @Override
    public List<EmployeeDTO> findByRole(EmployeeRole role) {
        log.debug("Request to get all Employees with role: {}", role);
        return employeeMapper.toDto(employeeRepository.findByRole(DOCTOR));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeDTO> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id)
            .map(employeeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
