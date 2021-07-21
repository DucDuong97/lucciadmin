package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.VideoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Video} and its DTO {@link VideoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VideoMapper extends EntityMapper<VideoDTO, Video> {



    default Video fromId(Long id) {
        if (id == null) {
            return null;
        }
        Video video = new Video();
        video.setId(id);
        return video;
    }
}
