package com.example.cnpm.quanlythuchinhatro.controller;

import com.example.cnpm.quanlythuchinhatro.dto.SmallTransactionDTO;
import com.example.cnpm.quanlythuchinhatro.dto.StatusSmallTransactionInRoomDTO;
import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;
import com.example.cnpm.quanlythuchinhatro.service.SmallTransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
public class SmallTransactionController {
    private final SmallTransactionService smallTransactionService;

    public SmallTransactionController(SmallTransactionService smallTransactionService) {
        this.smallTransactionService = smallTransactionService;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createSmallTransaction(@RequestBody SmallTransactionDTO smallTransactionDTO, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        smallTransactionService.createSmallTransaction(smallTransactionDTO, loggedInUser.toString());
        return ResponseEntity.ok(Map.of("message", "Tạo giao dịch thành công"));
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateSmallTransaction(@RequestParam("id") Integer id, @RequestBody SmallTransactionDTO smallTransaction, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        smallTransactionService.updateSmallTransaction(id, smallTransaction, loggedInUser.toString());
        return ResponseEntity.ok(Map.of("message","Giao dịch đã cập nhật thành công")) ;
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
    @GetMapping("/getStatusMoney")
    public ResponseEntity<StatusSmallTransactionInRoomDTO> getStatusMoney(
            @RequestParam Integer roomId,
            HttpSession session) {
        Object username = session.getAttribute("loggedInUser");
        StatusSmallTransactionInRoomDTO statusSmallTransactionInRoomDTO = smallTransactionService.getQuickStatus(roomId,(String) username);
        return ResponseEntity.ok(statusSmallTransactionInRoomDTO);
    }
}
