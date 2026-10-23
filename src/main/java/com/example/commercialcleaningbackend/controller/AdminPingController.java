package com.example.commercialcleaningbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminPingController {

    @GetMapping("/api/admin/ping")
    public String ping() {
        return "ok";
    }
}