package com.example.aspectdemo;

import com.example.aspectdemo.annotations.ArgsCatchable;
import com.example.aspectdemo.annotations.DurationTrackable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUuidService {

    @DurationTrackable
    @ArgsCatchable
    public UuidServiceResponse generateCustomUuid(Double rnd) throws Exception {
        if(rnd>= 0.5){

        UuidServiceResponse uuidServiceResponse = new UuidServiceResponse();
        uuidServiceResponse.setUuid("Custom_" + UUID.randomUUID().toString());
        //really hard work
        try {
            Thread.sleep((long) (Math.random() * 5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return uuidServiceResponse;
        }else {
            throw new Exception("rnd is less than 0.5");
        }
    }

    public UuidServiceResponse generateCustomUuid() {
        UuidServiceResponse uuidServiceResponse = new UuidServiceResponse();
        uuidServiceResponse.setUuid("Custom_" + UUID.randomUUID().toString());
        return uuidServiceResponse;
    }
}
