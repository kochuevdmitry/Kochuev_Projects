package com.example.string_stats.service;

import com.example.string_stats.dto.RequestsStatisticResponseDto;
import com.example.string_stats.dto.StatisticResponseDto;

import java.util.TreeMap;

public interface StatisticsService {

    RequestsStatisticResponseDto getRequestStatistic(String requestString);
    TreeMap<Character, StatisticResponseDto> getOverallStatistics();
}
