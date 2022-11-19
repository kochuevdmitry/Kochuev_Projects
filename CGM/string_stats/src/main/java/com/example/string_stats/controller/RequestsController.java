package com.example.string_stats.controller;

import com.example.string_stats.dto.RequestsStatisticResponseDto;
import com.example.string_stats.service.StatisticsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestsController {

    private final StatisticsServiceImpl statisticsServiceImpl;

    @PostMapping("/get/request")
    public ResponseEntity<RequestsStatisticResponseDto> getRequestString(@RequestParam String requestString){
        return new ResponseEntity<>(statisticsServiceImpl.getRequestStatistic(requestString), HttpStatus.OK);
    }

}
