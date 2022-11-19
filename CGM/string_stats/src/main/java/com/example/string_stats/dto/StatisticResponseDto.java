package com.example.string_stats.dto;

import lombok.Data;

@Data
public class StatisticResponseDto {
    private int countRequests;
    private double averageCountInRequests;
    private double averageCountMaxLenInRequests;
}
