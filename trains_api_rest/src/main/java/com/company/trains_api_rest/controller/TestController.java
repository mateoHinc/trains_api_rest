package com.company.trains_api_rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/tests")
public class TestController {
    @GetMapping
    public String test() {
        return "API working successfully";
    }
    
}
