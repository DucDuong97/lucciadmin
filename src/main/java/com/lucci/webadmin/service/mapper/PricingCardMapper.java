package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.PricingCardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PricingCard} and its DTO {@link PricingCardDTO}.
 */
@Mapper(componentModel = "spring")
public interface PricingCardMapper extends EntityMapper<PricingCardDTO, PricingCard> {

    PricingCardDTO toDto(PricingCard pricingCard);

    @Mapping(target = "contents", ignore = true)
    @Mapping(target = "removeContents", ignore = true)
    PricingCard toEntity(PricingCardDTO pricingCardDTO);

    default PricingCard fromId(Long id) {
        if (id == null) {
            return null;
        }
        PricingCard pricingCard = new PricingCard();
        pricingCard.setId(id);
        return pricingCard;
    }
}
