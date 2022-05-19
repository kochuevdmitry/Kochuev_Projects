package com.example.test1.controllers;

import com.example.test1.service.ExchangeService;
import com.example.test1.data.RequestsHistoryEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api("fx exchange request api")
public class ExchangeRequestsController {

    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeRequestsController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/exchange")
    @ApiOperation("request fx, value and output fx to get conversion, inputFX and outputFX 3 letter FX codes like USD and EUR")
    public ResponseEntity<RequestsHistoryEntity> booksByAuthor(@RequestParam("userId") int userId, @RequestParam("value") double value,
                                                               @RequestParam("inputFX") String inputFX, @RequestParam("outputFX") String outputFX) {
        return ResponseEntity.ok(exchangeService.getExchangeData(userId, value, inputFX, outputFX));
    }

    @GetMapping("/stats/above_usd_value/")
    @ApiOperation("request all user ids with transaction above provided value.")
    public ResponseEntity<List<Integer>> aboveUsdValue(@RequestParam("value") double value) {
        return exchangeService.getUsersWithRequestsAboveValueInUsd(value);
    }

    @GetMapping("/stats/summ_above_usd_value/")
    @ApiOperation("request all user ids with summ of transactions above provided value.")
    public ResponseEntity<List<Integer>> summAboveUsdValue(@RequestParam("value") double value) {
        return exchangeService.getUsersWithSummOfRequestsAboveValueInUsd(value);
    }

}
