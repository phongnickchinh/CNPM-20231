package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.StatusSmallTransactionInRoomDTO;
import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;
import com.example.cnpm.quanlythuchinhatro.repository.SmallTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SmallTransactionServiceImpl implements SmallTransactionService{
    private final SmallTransactionRepository smallTransactionRepository;

    @Autowired
    public SmallTransactionServiceImpl(SmallTransactionRepository smallTransactionRepository) {
        this.smallTransactionRepository = smallTransactionRepository;
    }

    @Override
    public void createSmallTransaction(SmallTransaction smallTransaction) {
        smallTransactionRepository.save(smallTransaction);
    }

    @Override
    public void updateSmallTransaction(Integer id, SmallTransaction smallTransaction) {
        smallTransaction.setId(id);
        smallTransactionRepository.save(smallTransaction);
    }

    @Override
    public void deleteSmallTransaction(Integer id) {
        smallTransactionRepository.deleteById(id);
    }

    @Override
    public List<SmallTransaction> getTransactions(Integer roomId, Integer year, Integer month, Integer userId) {
        String datePrefix = String.format("%d-%02d", year, month);
        if(userId != null) {
            return smallTransactionRepository.findByRoomIdAndUserIdAndTransactionTimeStartingWith(roomId, userId, datePrefix);
        }else {
            return smallTransactionRepository.findByRoomIdAndTransactionTimeStartingWith(roomId, datePrefix);
        }
    }

    @Override
    public StatusSmallTransactionInRoomDTO getQuickStatus(Integer roomId, Integer userId) {
        BigDecimal mySpent = smallTransactionRepository.sumSpentByUserInRoom(userId, roomId);
        BigDecimal roomAverage = smallTransactionRepository.averageSpentInRoom(roomId);
        return new StatusSmallTransactionInRoomDTO(mySpent, roomAverage);
    }
}
