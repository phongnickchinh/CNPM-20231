package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.SmallTransactionDTO;
import com.example.cnpm.quanlythuchinhatro.dto.StatusSmallTransactionInRoomDTO;
import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SmallTransactionService {
    ResponseEntity<?> createSmallTransaction(SmallTransactionDTO smallTransactionDTO, String userName);
    ResponseEntity<?> updateSmallTransaction(Integer id, SmallTransactionDTO smallTransactionDTO, String userName);
    public void deleteSmallTransaction(Integer id);
    public List<SmallTransaction> getTransactions(Integer roomId, Integer year, Integer month, Integer userId);
    StatusSmallTransactionInRoomDTO getQuickStatus(Integer roomId, String username);
}
