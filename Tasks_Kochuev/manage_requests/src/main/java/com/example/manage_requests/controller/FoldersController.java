package com.example.manage_requests.controller;

import com.example.manage_requests.dto.CommonResponseDto;
import com.example.manage_requests.dto.FoldersDto;
import com.example.manage_requests.dto.RequestDto;
import com.example.manage_requests.service.FoldersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("Folders controller api")
public class FoldersController {

    private final FoldersService foldersService;

    @PostMapping("/add/folder")
    @ApiOperation("Operation to add new folder")
    public ResponseEntity<CommonResponseDto> addNewFolder(@RequestParam String folderName) {
        return new ResponseEntity<>(foldersService.addFolder(folderName), HttpStatus.OK);
    }

    @GetMapping("/get/folders")
    @ApiOperation("Operation to get all folders")
    public ResponseEntity<List<FoldersDto>> getRequests() {
        return new ResponseEntity<>(foldersService.getAllFOlders(), HttpStatus.OK);
    }
}
