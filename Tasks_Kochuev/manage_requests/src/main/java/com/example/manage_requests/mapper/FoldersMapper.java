package com.example.manage_requests.mapper;

import com.example.manage_requests.dto.FoldersDto;
import com.example.manage_requests.entity.FoldersEntity;
import org.mapstruct.Mapper;

@Mapper
public interface FoldersMapper {
    FoldersDto foldersEntityToDto(FoldersEntity foldersEntity);
}
