package com.example.securityloginsignup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tests")
@RequiredArgsConstructor
public class TestController {

    @GetMapping
    public String test() {
        return "Test Controller";
    }

    @GetMapping("/public")
    public String publicTest() {
        return "This is public API";
    }
}
