package com.example.aspectdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomUuidServiceController {

    private final UuidService uuidService;

    @Autowired
    public RandomUuidServiceController(UuidService uuidService) {
        this.uuidService = uuidService;
    }

    @GetMapping("uuid")
    public ResponseEntity<?> handleUuid(){
        return ResponseEntity.ok(uuidService.generateUuid());
    }
}
