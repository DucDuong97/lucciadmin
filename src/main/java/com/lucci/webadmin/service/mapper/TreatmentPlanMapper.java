package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.TreatmentPlanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TreatmentPlan} and its DTO {@link TreatmentPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, PricingCardMapper.class, TreatmentMapper.class})
public interface TreatmentPlanMapper extends EntityMapper<TreatmentPlanDTO, TreatmentPlan> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "service.name", target = "serviceName")
    TreatmentPlanDTO toDto(TreatmentPlan treatmentPlan);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "serviceId", target = "service")
    @Mapping(target = "treatments", ignore = true)
    @Mapping(target = "removeTreatment", ignore = true)
    TreatmentPlan toEntity(TreatmentPlanDTO treatmentPlanDTO);

    default TreatmentPlan fromId(Long id) {
        if (id == null) {
            return null;
        }
        TreatmentPlan treatmentPlan = new TreatmentPlan();
        treatmentPlan.setId(id);
        return treatmentPlan;
    }
}
