package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.ImgUrlDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ImgUrl} and its DTO {@link ImgUrlDTO}.
 */
@Mapper(componentModel = "spring", uses = {TreatmentMapper.class, ServiceItemMapper.class})
public interface ImgUrlMapper extends EntityMapper<ImgUrlDTO, ImgUrl> {

    @Mapping(target = "serviceItems", ignore = true)
    @Mapping(target = "treatments", ignore = true)
    @Mapping(target = "imgUrl", expression = "java(entity.getPath()+\"/\"+entity.getName())")
    ImgUrlDTO toDto(ImgUrl entity);

    @Mapping(target = "removeServiceItems", ignore = true)
    @Mapping(target = "removeTreatment", ignore = true)
    ImgUrl toEntity(ImgUrlDTO dto);

    default ImgUrl fromId(Long id) {
        if (id == null) {
            return null;
        }
        ImgUrl imgUrl = new ImgUrl();
        imgUrl.setId(id);
        return imgUrl;
    }
}
