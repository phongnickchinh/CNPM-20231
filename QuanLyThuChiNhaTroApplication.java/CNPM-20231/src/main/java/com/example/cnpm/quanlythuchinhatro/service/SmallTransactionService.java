package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.StatusSmallTransactionInRoomDTO;
import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;

import java.util.List;

public interface SmallTransactionService {
    public void createSmallTransaction(SmallTransaction smallTransaction);
    public void updateSmallTransaction(Integer id, SmallTransaction smallTransaction);
    public void deleteSmallTransaction(Integer id);
    public List<SmallTransaction> getTransactions(Integer roomId, Integer year, Integer month, Integer userId);
    StatusSmallTransactionInRoomDTO getQuickStatus(Integer roomId, Integer userId);
}
