package com.example.string_stats.dto;

import lombok.Data;

import java.util.TreeMap;

@Data
public class RequestsStatisticResponseDto {
    private TreeMap<Character, RequestStatDto> requestStatistic;
}
