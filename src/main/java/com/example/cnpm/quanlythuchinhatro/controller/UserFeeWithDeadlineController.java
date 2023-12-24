package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.service.UserFeeWithDeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statusFeeWD")
public class UserFeeWithDeadlineController {
    private final UserFeeWithDeadlineService userFeeWithDeadlineService;
    @Autowired
    public UserFeeWithDeadlineController(UserFeeWithDeadlineService userFeeWithDeadlineService) {
        this.userFeeWithDeadlineService = userFeeWithDeadlineService;
    }
    @GetMapping("/all/{roomId}")
    public List<Object[]> getStatusFeeWithDeadline(@PathVariable("roomId") Integer roomId) {
        return userFeeWithDeadlineService.getStatusFeeWithDeadline(roomId);
    }

}
