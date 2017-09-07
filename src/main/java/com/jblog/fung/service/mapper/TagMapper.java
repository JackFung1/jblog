package com.jblog.fung.service.mapper;

import com.jblog.fung.domain.*;
import com.jblog.fung.service.dto.TagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tag and its DTO TagDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagMapper extends EntityMapper <TagDTO, Tag> {
    
    @Mapping(target = "entries", ignore = true)
    Tag toEntity(TagDTO tagDTO); 
    default Tag fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tag tag = new Tag();
        tag.setId(id);
        return tag;
    }
}
