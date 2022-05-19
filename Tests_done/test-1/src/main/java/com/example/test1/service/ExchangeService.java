package com.example.test1.service;

import com.example.test1.repository.RequestHistoryRepository;
import com.example.test1.data.RequestsHistoryEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ExchangeService {

    private RequestHistoryRepository requestHistoryRepository;

    @Autowired
    public ExchangeService(RequestHistoryRepository requestHistoryRepository) {
        this.requestHistoryRepository = requestHistoryRepository;
    }

    public RequestsHistoryEntity getExchangeData(int userId, double value, String inputFX, String outputFX) {
        RequestsHistoryEntity requestsHistory = new RequestsHistoryEntity();
        try {
            requestsHistory.setRequestFX(inputFX);
            requestsHistory.setOutputFX(outputFX);
            requestsHistory.setRequestValue(value);
            requestsHistory.setUserId(userId);
            requestsHistory.setSuccess(false);

            URL url = new URL("https://www.cbr-xml-daily.ru/latest.js");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode == 200) {
                Logger.getLogger(this.getClass().getSimpleName()).info("Response Conversion JSON Data Obtained - SUCCESS.");
                requestsHistory = getJSONTextFromUrlConnected(url, requestsHistory);
            } else {
                Logger.getLogger(this.getClass().getSimpleName())
                        .info("Response FAIL no data recieved for conversion, response code: " + responsecode);
            }
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getSimpleName()).info(e.getMessage());
            e.printStackTrace();
        }
        requestHistoryRepository.save(requestsHistory);
        return requestsHistory;
    }

    private RequestsHistoryEntity getJSONTextFromUrlConnected(URL url, RequestsHistoryEntity requestsHistory) {
        String inline = "";
        try {
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }
            scanner.close();
            requestsHistory = getConversionFromJSONandSave(requestsHistory, inline);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestsHistory;
    }

    private RequestsHistoryEntity getConversionFromJSONandSave(RequestsHistoryEntity requestsHistory, String inline) {
        try {
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(inline);

            //Get the required object from the above created object
            JSONObject obj = (JSONObject) data_obj.get("rates");

            //Get the required data using its key
            Double from = 1.0;
            Double to = 1.0;
            if (!requestsHistory.getRequestFX().equals("RUB")) {
                from = Double.parseDouble(obj.get(requestsHistory.getRequestFX()).toString());
            }
            if (!requestsHistory.getOutputFX().equals("RUB")) {
                to = Double.parseDouble(obj.get(requestsHistory.getOutputFX()).toString());
            }
            Logger.getLogger(this.getClass().getSimpleName()).info(from + " " + to);

            Double amount = requestsHistory.getRequestValue() / from;
            amount = amount * to;
            requestsHistory.setOutputValue(amount);
            requestsHistory.setSuccess(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestsHistory;
    }


    public ResponseEntity<List<Integer>> getUsersWithRequestsAboveValueInUsd(double value) {
        List<Integer> usersList = new ArrayList<>();
        List<RequestsHistoryEntity> requestsHistoryEntityList = requestHistoryRepository.findAll();
        if (requestsHistoryEntityList != null) {
            for (RequestsHistoryEntity requestsHistory : requestsHistoryEntityList) {
                if (convertToUSD(requestsHistory) > value) {
                    usersList.add(requestsHistory.getUserId());
                }
            }
        }
        return ResponseEntity.ok(usersList.stream().distinct().collect(Collectors.toList()));
    }

    private Double convertToUSD(RequestsHistoryEntity requestsHistory) {
        if (requestsHistory.getRequestFX().equals("USD")) {
            return requestsHistory.getRequestValue();
        }

        if (requestsHistory.getRequestFX().equals("RUB")) {
            return requestsHistory.getRequestValue() * getFXConversionJSON("USD");
        }

        return requestsHistory.getRequestValue() * getFXConversionJSON(requestsHistory.getRequestFX());
    }

    private Double getFXConversionJSON(String FX) {
        try {
            URL url = new URL("https://www.cbr-xml-daily.ru/latest.js");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();

            String inline = "";
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }
            scanner.close();

            if (responsecode == 200) {
                return getConversionFromJSON(FX, inline);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private Double getConversionFromJSON(String requestFX, String inline) {
        Double from = 1.0;
        Double to = 1.0;
        try {
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(inline);

            //Get the required object from the above created object
            JSONObject obj = (JSONObject) data_obj.get("rates");

            //Get the required data using its key

            if (!requestFX.equals("RUB")) {
                from = Double.parseDouble(obj.get(requestFX).toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return from;
    }


    public ResponseEntity<List<Integer>> getUsersWithSummOfRequestsAboveValueInUsd(double value) {
        Map<Integer, Double> usersList = new TreeMap<>();
        List<Integer> usersIdsSummAboveValue = new ArrayList<>();
        List<RequestsHistoryEntity> requestsHistoryEntityList = requestHistoryRepository.findAll();
        if (requestsHistoryEntityList != null) {
            for (RequestsHistoryEntity requestsHistory : requestsHistoryEntityList) {
                Double summValues = 0.0;
                if (usersList.get(requestsHistory.getUserId()) != null) {
                    summValues = usersList.get(requestsHistory.getUserId());
                }
                usersList.put(requestsHistory.getUserId(), summValues + convertToUSD(requestsHistory));
            }
            for (Integer key : usersList.keySet()) {
                if (usersList.get(key) > value) {
                    usersIdsSummAboveValue.add(key);
                }
            }
        }
        return ResponseEntity.ok(usersIdsSummAboveValue);
    }
}
