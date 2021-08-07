package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.*;
import com.lucci.webadmin.service.dto.AchievementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Achievement} and its DTO {@link AchievementDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AchievementMapper extends EntityMapper<AchievementDTO, Achievement> {



    default Achievement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Achievement achievement = new Achievement();
        achievement.setId(id);
        return achievement;
    }
}
