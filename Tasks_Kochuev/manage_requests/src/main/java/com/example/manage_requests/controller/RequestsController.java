package com.example.manage_requests.controller;

import com.example.manage_requests.dto.*;
import com.example.manage_requests.service.RequestsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("Requests controller api")
public class RequestsController {

    private final RequestsService requestsService;


    @PostMapping("/add/request")
    @ApiOperation("Operation to add new request")
    public ResponseEntity<CommonResponseDto> addRequest(@RequestBody RequestDto requestDto){
        return new ResponseEntity<>(requestsService.addRequest(requestDto), HttpStatus.OK);
    }

    @PutMapping("/assign/request_to_folder")
    @ApiOperation("Operation to assign request to folder by ids")
    public ResponseEntity<CommonResponseDto> assignRequestToFolder(
            @RequestBody RequestToFolderDto requestToFolderDto) {
        return new ResponseEntity<>(requestsService.assignRequestToFolder(requestToFolderDto), HttpStatus.OK);
    }

    @GetMapping("/get/requests")
    @ApiOperation("Operation to get all requests")
    public ResponseEntity<List<RequestDto>> getRequests() {
        return new ResponseEntity<>(requestsService.getAllRequests(), HttpStatus.OK);
    }

    @GetMapping("/get/requests_by_tag")
    @ApiOperation("Operation to get all requests by provided tagId")
    public ResponseEntity<List<RequestDto>> getRequestByTag(@RequestParam Long tagId) {
        return new ResponseEntity<>(requestsService.getRequestByTag(tagId), HttpStatus.OK);
    }

    @GetMapping("/get/requests_by_folder")
    @ApiOperation("Operation to get all requests by provided folderId")
    public ResponseEntity<List<RequestDto>> getRequestByFolder(@RequestParam Long folderId) {
        return new ResponseEntity<>(requestsService.getRequestByFolder(folderId), HttpStatus.OK);
    }
}
