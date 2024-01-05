package com.example.cnpm.quanlythuchinhatro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cnpm.quanlythuchinhatro.dto.MonthlyStatsRequest;
import com.example.cnpm.quanlythuchinhatro.dto.MonthlyStatsResponse;
import com.example.cnpm.quanlythuchinhatro.dto.TransactionStatisticsResponse;
import com.example.cnpm.quanlythuchinhatro.service.TransactionService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/last_month")
    public ResponseEntity<?> getMonthlyStats(@RequestBody MonthlyStatsRequest request, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        MonthlyStatsResponse response = transactionService.getMonthlyStats(request);

        return ResponseEntity.ok(response);
    }
}


