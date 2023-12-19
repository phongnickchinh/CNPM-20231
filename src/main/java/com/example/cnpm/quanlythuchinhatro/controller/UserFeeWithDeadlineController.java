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
    public List<UserFeeWithDeadline> getStatusFeeWithDeadlineByRoomId(@RequestParam("roomId") Integer roomId) {
        // Sử dụng service để lấy dữ liệu dựa theo roomId
        return userFeeWithDeadlineService.getStatusFeeWithDeadlineByRoomId(roomId);
    }
    @GetMapping("/")
    public String helo() {
        return "Hello Hồng Ngọc";
    }

}
