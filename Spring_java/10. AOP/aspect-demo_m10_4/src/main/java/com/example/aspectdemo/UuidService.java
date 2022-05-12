package com.example.aspectdemo;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UuidService {

    public UuidServiceResponse generateUuid(Double rnd) {
        UuidServiceResponse uuidServiceResponse = new UuidServiceResponse();
        uuidServiceResponse.setUuid(UUID.randomUUID().toString());
        //really hard work
        try {
            Thread.sleep((long) (rnd * 5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return uuidServiceResponse;
    }

    public UuidServiceResponse generateUuid() {
        UuidServiceResponse uuidServiceResponse = new UuidServiceResponse();
        uuidServiceResponse.setUuid(UUID.randomUUID().toString());
        //really hard work
        try {
            Thread.sleep((long) (Math.random() * 5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return uuidServiceResponse;
    }
}
