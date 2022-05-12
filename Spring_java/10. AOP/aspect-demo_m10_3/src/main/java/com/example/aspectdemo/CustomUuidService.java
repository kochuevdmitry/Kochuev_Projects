package com.example.aspectdemo;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUuidService {

    public UuidServiceResponse generateCustomUuid() {
        UuidServiceResponse uuidServiceResponse = new UuidServiceResponse();
        uuidServiceResponse.setUuid("Custom_" + UUID.randomUUID().toString());
        return uuidServiceResponse;
    }
}
