package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.dto.FeeWithDeadlineDTO;
import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.service.FeeWithDeadlineService;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/feewd")
public class FeeWithDeadlineController {
    private final FeeWithDeadlineService feeWithDeadlineService;

    public FeeWithDeadlineController(FeeWithDeadlineService feeWithDeadlineService) {
        this.feeWithDeadlineService = feeWithDeadlineService;
    }
    @PostMapping("/create")
    public ResponseEntity<?>  create(@RequestBody FeeWithDeadline feeWithDeadline) {
        feeWithDeadlineService.createFeeWithDeadline(feeWithDeadline);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Tạo chi phí có thời hạn thành công"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        feeWithDeadlineService.deleteFeeWithDeadline(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Delete a fee with deadline"));
    }
    @GetMapping("/getByRoomId/{roomId}")
    public ResponseEntity<List<FeeWithDeadline>>  getAllFeeWithDeadline(@PathVariable("roomId") Integer roomId) {
        List<FeeWithDeadline> list = feeWithDeadlineService.getAllFeeWithDeadline(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<FeeWithDeadline>  updateFeeWithDeadline(@PathVariable("id") Integer id, @RequestBody FeeWithDeadlineDTO feeWithDeadline) {
        FeeWithDeadline fee = feeWithDeadlineService.updateFeeWithDeadline(id, feeWithDeadline);
        return ResponseEntity.status(HttpStatus.OK).body(fee);
    }

}
