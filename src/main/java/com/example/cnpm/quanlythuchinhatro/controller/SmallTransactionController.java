package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.dto.StatusSmallTransactionInRoomDTO;
import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;
import com.example.cnpm.quanlythuchinhatro.service.SmallTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class SmallTransactionController {
    private final SmallTransactionService smallTransactionService;

    public SmallTransactionController(SmallTransactionService smallTransactionService) {
        this.smallTransactionService = smallTransactionService;
    }
    @PostMapping("/create")
    public String createSmallTransaction(@RequestBody SmallTransaction smallTransaction) {
        smallTransactionService.createSmallTransaction(smallTransaction);
        return "Giao dịch đã tạo thành công";
    }
    @PutMapping("/update/{id}")
    public String updateSmallTransaction(@PathVariable("id") Integer id, @RequestBody SmallTransaction smallTransaction) {
        smallTransactionService.updateSmallTransaction(id, smallTransaction);
        return "Giao dịch đã cập nhật thành công";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteSmallTransaction(@PathVariable("id") Integer id) {
        smallTransactionService.deleteSmallTransaction(id);
        return "Xóa giao dịch thành công";
    }

    @GetMapping("/getByRoomId/{roomId}/{year}/{month}/{userId}")
    public ResponseEntity<List<SmallTransaction>> getTransactionsByRoomId(
            @PathVariable Integer roomId,
            @PathVariable Integer year,
            @PathVariable Integer month,
            @PathVariable(required = false) Integer userId) {
        List<SmallTransaction> transactions = smallTransactionService.getTransactions(roomId, year, month, userId);
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/getByRoomId/{roomId}/{year}/{month}")
    public ResponseEntity<List<SmallTransaction>> getTransactionsByRoomId(
            @PathVariable Integer roomId,
            @PathVariable Integer year,
            @PathVariable Integer month) {
        return ResponseEntity.ok(getTransactionsByRoomId(roomId, year, month, null).getBody());
    }
    @GetMapping("/getStatusMoney/{roomId}/{userId}")
    public ResponseEntity<StatusSmallTransactionInRoomDTO> getStatusMoney(
            @PathVariable Integer roomId,
            @PathVariable Integer userId) {
        StatusSmallTransactionInRoomDTO statusSmallTransactionInRoomDTO = smallTransactionService.getQuickStatus(roomId, userId);
        return ResponseEntity.ok(statusSmallTransactionInRoomDTO);
    }
}
