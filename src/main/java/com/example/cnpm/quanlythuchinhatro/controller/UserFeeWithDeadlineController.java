package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.service.UserFeeWithDeadlineService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/userFeeWD")
public class UserFeeWithDeadlineController {
    private final UserFeeWithDeadlineService userFeeWithDeadlineService;

    public UserFeeWithDeadlineController(UserFeeWithDeadlineService userFeeWithDeadlineService) {
        this.userFeeWithDeadlineService = userFeeWithDeadlineService;
    }
    @GetMapping("/getAll")
    public List<UserFeeWithDeadline> getStatusFeeWithDeadline(@RequestParam("id") Integer id) {
        return userFeeWithDeadlineService.getStatusFeeWithDeadline(id);
    }
}
