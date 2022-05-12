package com.example.aspectdemo;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUuidService {

    public UuidServiceResponse generateCustomUuid(Double rnd) {
        UuidServiceResponse uuidServiceResponse = new UuidServiceResponse();
        uuidServiceResponse.setUuid("Custom_" + UUID.randomUUID().toString());
        //really hard work
        try {
            Thread.sleep((long) (Math.random() * 5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return uuidServiceResponse;
    }

    public UuidServiceResponse generateCustomUuid() {
        UuidServiceResponse uuidServiceResponse = new UuidServiceResponse();
        uuidServiceResponse.setUuid("Custom_" + UUID.randomUUID().toString());
        return uuidServiceResponse;
    }
}
