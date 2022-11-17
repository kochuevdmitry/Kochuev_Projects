package com.example.manage_requests.service;

import com.example.manage_requests.dto.CommonResponseDto;
import com.example.manage_requests.dto.FoldersDto;
import com.example.manage_requests.entity.FoldersEntity;
import com.example.manage_requests.entity.TagsEntity;
import com.example.manage_requests.mapper.FoldersMapper;
import com.example.manage_requests.repository.FoldersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoldersService {

    private final FoldersRepository foldersRepository;
    private final FoldersMapper foldersMapper;
    public CommonResponseDto addFolder(String folderName) {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        if (foldersRepository.findByFolderName(folderName) != null){
            commonResponseDto.setError("This folder already exists.");
            return commonResponseDto;
        }
        FoldersEntity foldersEntity = new FoldersEntity();
        foldersEntity.setFolderName(folderName);
        foldersRepository.save(foldersEntity);
        commonResponseDto.setMessage("folder saved");
        return commonResponseDto;
    }

    public List<FoldersDto> getAllFOlders() {
        return foldersRepository.findAll().stream().map(foldersMapper::foldersEntityToDto).toList();
    }
}
