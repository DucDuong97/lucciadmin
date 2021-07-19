package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.TreatmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Treatment} and its DTO {@link TreatmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, ImgUrlMapper.class, TreatmentPlanMapper.class})
public interface TreatmentMapper extends EntityMapper<TreatmentDTO, Treatment> {

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "doctor.name", target = "doctorName")
    @Mapping(source = "treatmentPlan.id", target = "treatmentPlanId")
    TreatmentDTO toDto(Treatment treatment);

    @Mapping(source = "doctorId", target = "doctor")
    @Mapping(target = "removeTreatmentImgUrl", ignore = true)
    @Mapping(source = "treatmentPlanId", target = "treatmentPlan")
    Treatment toEntity(TreatmentDTO treatmentDTO);

    default Treatment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Treatment treatment = new Treatment();
        treatment.setId(id);
        return treatment;
    }
}
