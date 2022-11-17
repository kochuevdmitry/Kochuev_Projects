package com.example.manage_requests.mapper;

import com.example.manage_requests.dto.TagsDto;
import com.example.manage_requests.entity.TagsEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TagsMapper {

    TagsEntity tagsDtoToEntity(TagsDto tagsDto);
    TagsDto tagsEntityToDto(TagsEntity tagsEntity);
}
