package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {BranchMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

    @Mapping(source = "workAt.id", target = "workAtId")
    EmployeeDTO toDto(Employee employee);

    @Mapping(source = "workAtId", target = "workAt")
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
