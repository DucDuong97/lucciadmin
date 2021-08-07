package com.lucci.webadmin.service.mapper;


import com.lucci.webadmin.domain.ImgUrl;
import com.lucci.webadmin.domain.Process;
import com.lucci.webadmin.service.dto.ProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Process} and its DTO {@link ProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = {ServiceItemMapper.class, ImgUrlMapper.class})
public interface ProcessMapper extends EntityMapper<ProcessDTO, Process> {

    @Mapping(source = "serviceItem.id", target = "serviceItemId")
    @Mapping(source = "serviceItem.name", target = "serviceItemName")
    @Mapping(source = "icon.id", target = "iconId")
    @Mapping(source = "icon", target = "iconName", qualifiedByName = "toUrl")
    ProcessDTO toDto(Process process);

    @Mapping(source = "serviceItemId", target = "serviceItem")
    @Mapping(source = "iconId", target = "icon")
    Process toEntity(ProcessDTO processDTO);

    default Process fromId(Long id) {
        if (id == null) {
            return null;
        }
        Process process = new Process();
        process.setId(id);
        return process;
    }
}
