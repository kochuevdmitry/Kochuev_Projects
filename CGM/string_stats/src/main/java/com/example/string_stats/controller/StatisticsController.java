package com.example.string_stats.controller;

import com.example.string_stats.dto.StatisticResponseDto;
import com.example.string_stats.service.StatisticsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsServiceImpl statisticsServiceImpl;

    @GetMapping("/get/statistic")
    public ResponseEntity<TreeMap<Character, StatisticResponseDto>> getStatistics(){
        return new ResponseEntity<>(statisticsServiceImpl.getOverallStatistics(), HttpStatus.OK);
    }

}
