package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.SmallTransactionDTO;
import com.example.cnpm.quanlythuchinhatro.dto.StatusSmallTransactionInRoomDTO;
import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SmallTransactionService {
    ResponseEntity<SmallTransaction> createSmallTransaction(SmallTransactionDTO smallTransactionDTO, String userName);
    ResponseEntity<SmallTransaction> updateSmallTransaction(Integer id, SmallTransactionDTO smallTransactionDTO, String userName);
    public void deleteSmallTransaction(Integer id);
    public List<Map<String, Object>> getTransactions(Integer roomId, Integer year, Integer month, Integer userId);
    StatusSmallTransactionInRoomDTO getQuickStatus(Integer roomId, String username);
}
