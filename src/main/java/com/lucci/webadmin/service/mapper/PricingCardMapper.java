package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.PricingCardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PricingCard} and its DTO {@link PricingCardDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceItemMapper.class, PricingContentMapper.class, ImgUrlMapper.class})
public interface PricingCardMapper extends EntityMapper<PricingCardDTO, PricingCard> {

    @Mapping(source = "serviceItem.id", target = "serviceItemId")
    @Mapping(source = "serviceItem.name", target = "serviceItemName")
    @Mapping(source = "imgUrl.id", target = "imgUrlId")
    @Mapping(source = "imgUrl.name", target = "imgUrlName")
    PricingCardDTO toDto(PricingCard pricingCard);

    @Mapping(target = "removePricingContent", ignore = true)
    @Mapping(source = "serviceItemId", target = "serviceItem")
    @Mapping(source = "imgUrlId", target = "imgUrl")
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
