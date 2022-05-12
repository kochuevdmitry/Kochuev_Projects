package com.example.aspectdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomUuidServiceController {

    private final UuidService uuidService;
    private final CustomUuidService customUuidService;

    @Autowired
    public RandomUuidServiceController(UuidService uuidService, CustomUuidService customUuidService) {
        this.uuidService = uuidService;
        this.customUuidService = customUuidService;
    }

    @GetMapping("/uuid")
    public ResponseEntity<?> handleUuid() {
        return ResponseEntity.ok(uuidService.generateUuid(Math.random()));
    }

    @GetMapping("/uuid/custom")
    public ResponseEntity<?> handleCustomUuid() throws Exception {
        return ResponseEntity.ok(customUuidService.generateCustomUuid(Math.random()));
    }

}
