package com.github.railway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ankit Kashyap on 08-03-2024
 */
@RestController
public class HealthCheck {
    @GetMapping("/health")
    public ResponseEntity<String> checkHealth(){
        return ResponseEntity.ok("Okay,api is up");
    }
}
