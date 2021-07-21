package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.ServiceItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceItem} and its DTO {@link ServiceItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ImgUrlMapper.class, VideoMapper.class})
public interface ServiceItemMapper extends EntityMapper<ServiceItemDTO, ServiceItem> {

    @Mapping(source = "icon.id", target = "iconId")
    @Mapping(source = "icon", target = "iconName", qualifiedByName = "toUrl")
    ServiceItemDTO toDto(ServiceItem serviceItem);

    @Mapping(source = "iconId", target = "icon")
    @Mapping(target = "removeCustomerImgUrls", ignore = true)
    @Mapping(target = "processes", ignore = true)
    @Mapping(target = "removeProcesses", ignore = true)
    @Mapping(target = "relatedBlogs", ignore = true)
    @Mapping(target = "removeRelatedBlogs", ignore = true)
    @Mapping(target = "removeRelatedVideos", ignore = true)
    @Mapping(target = "pricingCards", ignore = true)
    @Mapping(target = "removePricingCards", ignore = true)
    ServiceItem toEntity(ServiceItemDTO serviceItemDTO);

    default ServiceItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setId(id);
        return serviceItem;
    }

    @Named("toUrl")
    static String ImgUrlToUrl(ImgUrl imgUrl) {
        if (imgUrl == null) {
            return null;
        }
        return imgUrl.getPath() + "/" + imgUrl.getName();
    }
}
