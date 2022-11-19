package com.example.string_stats.service;

import com.example.string_stats.dto.RequestStatDto;
import com.example.string_stats.dto.RequestsStatisticResponseDto;
import com.example.string_stats.dto.StatDto;
import com.example.string_stats.dto.StatisticResponseDto;
import com.example.string_stats.storage.StatisticStorage;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    public RequestsStatisticResponseDto getRequestStatistic(String requestString) {
        if (requestString.isEmpty()) {
            return new RequestsStatisticResponseDto();
        }
        RequestsStatisticResponseDto requestsStatisticResponseDto = new RequestsStatisticResponseDto();
        requestsStatisticResponseDto.setRequestStatistic(calcStat(requestString));
        addDataToStatisticStorage(requestsStatisticResponseDto);
        return requestsStatisticResponseDto;
    }

    private void addDataToStatisticStorage(RequestsStatisticResponseDto requestsStatisticResponseDto) {
        synchronized (StatisticStorage.getInstance()) {
            TreeMap<Character, StatDto> currentStatistic = StatisticStorage.getInstance().getStatisticsData();

            for (Character charAtRequest : requestsStatisticResponseDto.getRequestStatistic().keySet()) {
                StatDto statDto = new StatDto();
                statDto.setCountRequests(1);
                RequestStatDto requestStatDto = requestsStatisticResponseDto.getRequestStatistic().get(charAtRequest);
                statDto.setTotalCountInRequests(requestStatDto.getCount());
                statDto.setTotalCountMaxLenInRequests(requestStatDto.getMaxLen());
                if (currentStatistic.get(charAtRequest) != null) {
                    StatDto currStatDto = currentStatistic.get(charAtRequest);
                    statDto.setCountRequests(currStatDto.getCountRequests() + 1);
                    statDto.setTotalCountInRequests(statDto.getTotalCountInRequests() + currStatDto.getTotalCountInRequests());
                    statDto.setTotalCountMaxLenInRequests(statDto.getTotalCountMaxLenInRequests() + currStatDto.getTotalCountMaxLenInRequests());
                }
                currentStatistic.put(charAtRequest, statDto);
            }
        }

    }

    private TreeMap<Character, RequestStatDto> calcStat(String request) {
        int len = request.length();
        TreeMap<Character, RequestStatDto> requestStatDtoTreeMap = new TreeMap<>();

        Set<Character> exclusion = new HashSet<>();
        for (int i = 0; i < len; i++) {
            Character charAt = request.charAt(i);
            int count = requestStatDtoTreeMap.get(charAt) != null ? requestStatDtoTreeMap.get(charAt).getCount() + 1 : 1;
            RequestStatDto requestStatDto = new RequestStatDto();
            requestStatDto.setCount(count);
            if (!exclusion.contains(charAt)) {
                requestStatDto.setMaxLen(checkMaxLen(request, charAt, i));
                exclusion.add(charAt);
            } else {
                requestStatDto.setMaxLen(requestStatDtoTreeMap.get(charAt).getMaxLen());
            }
            requestStatDtoTreeMap.put(charAt, requestStatDto);
        }
        return requestStatDtoTreeMap;
    }

    private int checkMaxLen(String request, Character charAt, int pos) {
        int maxLen = 0;
        int currLen = 0;
        int len = request.length();
        for (int i = 0; i < len; i++) {
            if (charAt.equals(request.charAt(i))) {
                currLen++;
                maxLen = Math.max(currLen, maxLen);
            } else {
                currLen = 0;
            }
        }
        return maxLen;
    }

    public TreeMap<Character, StatisticResponseDto> getOverallStatistics() {
        synchronized (StatisticStorage.getInstance()) {
            TreeMap<Character, StatDto> statistics = StatisticStorage.getInstance().getStatisticsData();
            TreeMap<Character, StatisticResponseDto> responseStatistics = new TreeMap<>();

            for (Character nextChar : statistics.keySet()) {
                responseStatistics.put(nextChar, calcAveStat(statistics.get(nextChar)));
            }
            return responseStatistics;
        }
    }

    private StatisticResponseDto calcAveStat(StatDto statDto) {
        StatisticResponseDto statisticResponseDto = new StatisticResponseDto();
        statisticResponseDto.setCountRequests(statDto.getCountRequests());
        statisticResponseDto.setAverageCountInRequests((double) statDto.getTotalCountInRequests() / statDto.getCountRequests());
        statisticResponseDto.setAverageCountMaxLenInRequests((double) statDto.getTotalCountMaxLenInRequests() / statDto.getCountRequests());
        return statisticResponseDto;
    }
}
