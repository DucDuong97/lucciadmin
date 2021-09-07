package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.BannerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Banner} and its DTO {@link BannerDTO}.
 */
@Mapper(componentModel = "spring", uses = {ImgUrlMapper.class})
public interface BannerMapper extends EntityMapper<BannerDTO, Banner> {

    @Mapping(source = "imgUrl.id", target = "imgUrlId")
    @Mapping(source = "imgUrl.name", target = "imgUrlName")
    BannerDTO toDto(Banner banner);

    @Mapping(source = "imgUrlId", target = "imgUrl")
    Banner toEntity(BannerDTO bannerDTO);

    default Banner fromId(Long id) {
        if (id == null) {
            return null;
        }
        Banner banner = new Banner();
        banner.setId(id);
        return banner;
    }
}
