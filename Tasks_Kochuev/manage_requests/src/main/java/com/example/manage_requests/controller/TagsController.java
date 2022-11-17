package com.example.manage_requests.controller;

import com.example.manage_requests.dto.CommonResponseDto;
import com.example.manage_requests.dto.RequestDto;
import com.example.manage_requests.dto.RequestToTagDto;
import com.example.manage_requests.dto.TagsDto;
import com.example.manage_requests.service.TagsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("Tags controller api")
public class TagsController {

    private final TagsService tagsService;

    @PostMapping("/add/tag")
    @ApiOperation("Operation to add new tag")
    public ResponseEntity<CommonResponseDto> addNewTag(@RequestParam String tagName) {
        return new ResponseEntity<>(tagsService.addTag(tagName), HttpStatus.OK);
    }

    @PutMapping("/assign/tag_to_request")
    @ApiOperation("Operation to assign tag to request by id")
    public ResponseEntity<CommonResponseDto> assignTagToRequest(
            @RequestBody RequestToTagDto requestToTagDto) {
        return new ResponseEntity<>(tagsService.assignTagToRequest(requestToTagDto), HttpStatus.OK);
    }

    @GetMapping("/get/tags")
    @ApiOperation("Operation to get all tags")
    public ResponseEntity<List<TagsDto>> getTags() {
        return new ResponseEntity<>(tagsService.getAllTags(), HttpStatus.OK);
    }
}
