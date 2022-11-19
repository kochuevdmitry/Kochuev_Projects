package com.example.string_stats.dto;

import lombok.Data;

@Data
public class StatDto {
    private int countRequests;
    private int totalCountInRequests;
    private int totalCountMaxLenInRequests;
}
