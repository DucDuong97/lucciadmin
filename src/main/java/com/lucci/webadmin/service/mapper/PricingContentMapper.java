package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.PricingContentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PricingContent} and its DTO {@link PricingContentDTO}.
 */
@Mapper(componentModel = "spring", uses = {PricingCardMapper.class})
public interface PricingContentMapper extends EntityMapper<PricingContentDTO, PricingContent> {

    @Mapping(source = "pricingCard.id", target = "pricingCardId")
    @Mapping(source = "pricingCard.name", target = "pricingCardName")
    PricingContentDTO toDto(PricingContent pricingContent);

    @Mapping(source = "pricingCardId", target = "pricingCard")
    PricingContent toEntity(PricingContentDTO pricingContentDTO);

    default PricingContent fromId(Long id) {
        if (id == null) {
            return null;
        }
        PricingContent pricingContent = new PricingContent();
        pricingContent.setId(id);
        return pricingContent;
    }
}
