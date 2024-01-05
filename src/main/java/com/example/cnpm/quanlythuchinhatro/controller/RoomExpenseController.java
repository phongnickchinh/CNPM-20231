package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.dto.RoomExpenseDTO;
import com.example.cnpm.quanlythuchinhatro.service.RoomExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room-expenses")
public class RoomExpenseController {
    @Autowired
    private RoomExpenseService roomExpenseService;

    @GetMapping
    public ResponseEntity<List<RoomExpenseDTO>> getMonthlyRoomExpenses(@RequestParam int month, @RequestParam int year) {
        List<RoomExpenseDTO> expenses = roomExpenseService.calculateMonthlyExpenses(month, year);
        return ResponseEntity.ok(expenses);
    }
}
