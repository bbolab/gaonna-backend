package com.bbolab.gaonna.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class SampleController {
    @GetMapping()
    public ResponseEntity<?> get() {
        return ResponseEntity.ok("hello, world!");
    }
}
