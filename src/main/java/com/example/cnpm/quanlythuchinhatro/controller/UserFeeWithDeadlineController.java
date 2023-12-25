package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.service.UserFeeWithDeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statusFeeWD")
public class UserFeeWithDeadlineController {
    private final UserFeeWithDeadlineService userFeeWithDeadlineService;
    @Autowired
    public UserFeeWithDeadlineController(UserFeeWithDeadlineService userFeeWithDeadlineService) {
        this.userFeeWithDeadlineService = userFeeWithDeadlineService;
    }
    @GetMapping("/getAll/{roomId}")
    public List<Object[]> getStatusFeeWithDeadline(@PathVariable("roomId") Integer roomId) {
        return userFeeWithDeadlineService.getStatusFeeWithDeadline(roomId);
    }
    @GetMapping("/getUser/{roomId}/{userId}")
    public List<Object[]> getUserStatusFeeWithDeadline(@PathVariable("roomId") Integer roomId, @PathVariable("userId") Integer userId) {
        return userFeeWithDeadlineService.getUserStatusFeeWithDeadline(roomId, userId);
    }
    @PatchMapping("/changeStatus/{userId}/{feeId}")
    public ResponseEntity<String> toggleFeeStatus(@PathVariable Integer userId, @PathVariable Integer feeId) {
        try {
            boolean success = userFeeWithDeadlineService.toggleFeeStatus(userId, feeId);
            if (success) {
                return ResponseEntity.ok("Trạng thái chi phí đã được đảo ngược thành công.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy chi phí với thông tin cung cấp.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi đảo ngược trạng thái chi phí.");
        }
    }

}
