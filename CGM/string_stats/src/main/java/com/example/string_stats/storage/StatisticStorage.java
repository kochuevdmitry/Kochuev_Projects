package com.example.string_stats.storage;

import com.example.string_stats.dto.StatDto;

import java.util.TreeMap;

public class StatisticStorage {
    private static final TreeMap<Character, StatDto> STATISTIC = new TreeMap<>();

    private StatisticStorage() {
    }

    private static class StatisticStorageHolder {
        public static final StatisticStorage HOLDER_INSTANCE = new StatisticStorage();
    }

    public static StatisticStorage getInstance() {
        return StatisticStorage.StatisticStorageHolder.HOLDER_INSTANCE;
    }

    public TreeMap<Character, StatDto> getStatisticsData(){
        synchronized (STATISTIC) {
            return STATISTIC;
        }
    }

}
