package com.example.websmartspending.controller;

import com.example.websmartspending.service.FeeWithDeadlineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feewd")
public class FeeWithDeadlineController {
    private final FeeWithDeadlineService feeWithDeadlineService;
    public FeeWithDeadlineController(FeeWithDeadlineService feeWithDeadlineService) {
        this.feeWithDeadlineService = feeWithDeadlineService;
    }
    @GetMapping
    public String test () {
        return "Hello Jenky";
    }
}
