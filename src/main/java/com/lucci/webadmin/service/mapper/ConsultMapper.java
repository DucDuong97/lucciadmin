package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.ConsultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Consult} and its DTO {@link ConsultDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, EmployeeMapper.class, PricingCardMapper.class})
public interface ConsultMapper extends EntityMapper<ConsultDTO, Consult> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "consultingDoctor.id", target = "consultingDoctorId")
    @Mapping(source = "consultingDoctor.name", target = "consultingDoctorName")
    ConsultDTO toDto(Consult consult);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "consultingDoctorId", target = "consultingDoctor")
    @Mapping(target = "removeService", ignore = true)
    Consult toEntity(ConsultDTO consultDTO);

    default Consult fromId(Long id) {
        if (id == null) {
            return null;
        }
        Consult consult = new Consult();
        consult.setId(id);
        return consult;
    }
}
