package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.ImgUrlDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ImgUrl} and its DTO {@link ImgUrlDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImgUrlMapper extends EntityMapper<ImgUrlDTO, ImgUrl> {



    default ImgUrl fromId(Long id) {
        if (id == null) {
            return null;
        }
        ImgUrl imgUrl = new ImgUrl();
        imgUrl.setId(id);
        return imgUrl;
    }
}
