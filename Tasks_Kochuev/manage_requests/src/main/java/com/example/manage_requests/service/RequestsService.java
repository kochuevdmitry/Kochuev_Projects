package com.example.manage_requests.service;

import com.example.manage_requests.dto.CommonResponseDto;
import com.example.manage_requests.dto.RequestDto;
import com.example.manage_requests.dto.RequestToFolderDto;
import com.example.manage_requests.entity.FoldersEntity;
import com.example.manage_requests.entity.RequestEntity;
import com.example.manage_requests.entity.TagsEntity;
import com.example.manage_requests.mapper.RequestMapper;
import com.example.manage_requests.repository.FoldersRepository;
import com.example.manage_requests.repository.RequestsRepository;
import com.example.manage_requests.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestsService {

    private final RequestMapper requestMapper;
    private final RequestsRepository requestRepository;
    private final FoldersRepository foldersRepository;
    private final TagsRepository tagsRepository;

    @Transactional
    public CommonResponseDto addRequest(RequestDto requestDto) {
        RequestEntity requestEntity = requestMapper.requestDtoToEntity(requestDto);
        requestEntity.setLength(requestDto.getText().length());
        requestRepository.save(requestEntity);
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto.setMessage("request saved (no check if such request was before because who know...)");
        return commonResponseDto;
    }

    public CommonResponseDto assignRequestToFolder(RequestToFolderDto requestToFolderDto) {
        CommonResponseDto commonResponseDto = new CommonResponseDto();

        RequestEntity requestEntity = requestRepository.findById(requestToFolderDto.getRequestId()).orElseThrow();
        if (requestEntity.getFolder() != null &&
                requestEntity.getFolder().getId() == requestToFolderDto.getFolderId()) {
            commonResponseDto.setMessage("THis folder was already assigned.");
            return commonResponseDto;
        }
        FoldersEntity foldersEntity = foldersRepository.findById(requestToFolderDto.getFolderId()).orElseThrow();

        requestEntity.setFolder(foldersEntity);
        requestRepository.save(requestEntity);
        commonResponseDto.setMessage("Request assigned to folder.");
        return commonResponseDto;
    }

    public List<RequestDto> getAllRequests() {
        return requestRepository.findAll().stream()
                .map(requestMapper::requestEntityToDto).toList();
    }

    public List<RequestDto> getRequestByTag(Long tagId) {
        TagsEntity tagsEntity = tagsRepository.findById(tagId).orElseThrow();
        return tagsEntity.getRequestEntityList().stream()
                .map(requestMapper::requestEntityToDto).toList();
    }

    public List<RequestDto> getRequestByFolder(Long folderId) {
        FoldersEntity foldersEntity = foldersRepository.findById(folderId).orElseThrow();
        return foldersEntity.getRequestEntityList().stream()
                .map(requestMapper::requestEntityToDto).toList();
    }
}
