package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.PotentialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Potential} and its DTO {@link PotentialDTO}.
 */
@Mapper(componentModel = "spring", uses = {PricingCardMapper.class, BranchMapper.class})
public interface PotentialMapper extends EntityMapper<PotentialDTO, Potential> {

    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "service.name", target = "serviceName")
    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.adress", target = "branchAdress")
    PotentialDTO toDto(Potential potential);

    @Mapping(source = "serviceId", target = "service")
    @Mapping(source = "branchId", target = "branch")
    Potential toEntity(PotentialDTO potentialDTO);

    default Potential fromId(Long id) {
        if (id == null) {
            return null;
        }
        Potential potential = new Potential();
        potential.setId(id);
        return potential;
    }
}
