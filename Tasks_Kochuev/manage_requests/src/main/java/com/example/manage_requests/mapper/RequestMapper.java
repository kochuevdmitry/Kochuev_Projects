package com.example.manage_requests.mapper;

import com.example.manage_requests.dto.RequestDto;
import com.example.manage_requests.entity.RequestEntity;
import org.mapstruct.Mapper;

@Mapper
public interface RequestMapper {

    RequestEntity requestDtoToEntity(RequestDto requestDto);

    RequestDto requestEntityToDto(RequestEntity request);
}
