package com.example.manage_requests.service;

import com.example.manage_requests.dto.CommonResponseDto;
import com.example.manage_requests.dto.RequestToTagDto;
import com.example.manage_requests.dto.TagsDto;
import com.example.manage_requests.entity.RequestEntity;
import com.example.manage_requests.entity.TagsEntity;
import com.example.manage_requests.mapper.TagsMapper;
import com.example.manage_requests.repository.RequestsRepository;
import com.example.manage_requests.repository.TagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagsService {

    private static final Integer TAGS_LIMIT = 10;

    private final TagsRepository tagsRepository;
    private final RequestsRepository requestsRepository;
    private final TagsMapper tagsMapper;

    public CommonResponseDto addTag(String tagName) {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        if (tagsRepository.findByTagName(tagName) != null) {
            commonResponseDto.setError("This tag already exists.");
            return commonResponseDto;
        }
        TagsEntity tagsEntity = new TagsEntity();
        tagsEntity.setTagName(tagName);
        tagsRepository.save(tagsEntity);
        commonResponseDto.setMessage("tag saved");
        return commonResponseDto;
    }

    @Transactional
    public CommonResponseDto assignTagToRequest(RequestToTagDto requestToTagDto) {
        CommonResponseDto commonResponseDto = new CommonResponseDto();

        if (tagsRepository.findTagToRequest(requestToTagDto.getTagId(), requestToTagDto.getRequestId())) {
            commonResponseDto.setError("This tag already assigned to the request.");
            return commonResponseDto;
        }

        if (tagsRepository.checkTagsLimit(requestToTagDto.getRequestId()) >= TAGS_LIMIT) {
            commonResponseDto.setError("Limited capacity of tags is 10 already reached, no new tags assigned to request.");
            return commonResponseDto;
        }

        doUpdate(requestToTagDto);
        commonResponseDto.setMessage(String.format("tag %s added to request %s", requestToTagDto.getTagId(), requestToTagDto.getRequestId()));
        return commonResponseDto;
    }

    private void doUpdate(RequestToTagDto requestToTagDto) {
        TagsEntity tagsEntity = tagsRepository.findById(requestToTagDto.getTagId()).orElseThrow();
        RequestEntity requestEntity = requestsRepository.findById(requestToTagDto.getRequestId()).orElseThrow();
        List<TagsEntity> tagsEntityList = requestEntity.getTagsEntityList();
        if (tagsEntityList == null) {
            tagsEntityList = new ArrayList<>();
        }
        tagsEntityList.add(tagsEntity);
        requestEntity.setTagsEntityList(tagsEntityList);
        requestsRepository.save(requestEntity);
    }

    public List<TagsDto> getAllTags() {
        return tagsRepository.findAll().stream().map(tagsMapper::tagsEntityToDto).toList();
    }


}
