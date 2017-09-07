package com.jblog.fung.service.mapper;

import com.jblog.fung.domain.*;
import com.jblog.fung.service.dto.BlogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Blog and its DTO BlogDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface BlogMapper extends EntityMapper <BlogDTO, Blog> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    BlogDTO toDto(Blog blog); 

    @Mapping(source = "userId", target = "user")
    Blog toEntity(BlogDTO blogDTO); 
    default Blog fromId(Long id) {
        if (id == null) {
            return null;
        }
        Blog blog = new Blog();
        blog.setId(id);
        return blog;
    }
}
