package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.FeeWithDeadlineDTO;
import com.example.cnpm.quanlythuchinhatro.dto.SmallTransactionDTO;
import com.example.cnpm.quanlythuchinhatro.dto.StatusSmallTransactionInRoomDTO;
import com.example.cnpm.quanlythuchinhatro.dto.UserDTO;
import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.repository.SmallTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SmallTransactionServiceImpl implements SmallTransactionService{
    private final SmallTransactionRepository smallTransactionRepository;

    @Autowired
    public SmallTransactionServiceImpl(SmallTransactionRepository smallTransactionRepository) {
        this.smallTransactionRepository = smallTransactionRepository;
    }

    @Override
    public ResponseEntity<?> createSmallTransaction(SmallTransactionDTO smallTransactionDTO, String userName) {
        SmallTransaction smallTransaction = new SmallTransaction();
        Integer userId = smallTransactionRepository.convertUsernameToUserId(userName);
        smallTransaction.setItemName(smallTransactionDTO.getItemName());
        smallTransaction.setTransactionTime(smallTransactionDTO.getTransactionTime());
        smallTransaction.setPrice(smallTransactionDTO.getPrice());
        smallTransaction.setRoomId(smallTransactionDTO.getRoomId());
        smallTransaction.setUserId(userId);
        smallTransactionRepository.save(smallTransaction);

        return ResponseEntity.status(HttpStatus.OK).body("Giao dịch đã tạo thành công");
    }

    @Override
    public ResponseEntity<?> updateSmallTransaction(Integer id, SmallTransactionDTO smallTransactionDTO, String userName) {
        Optional<SmallTransaction> smallDb = smallTransactionRepository.findById(id);
        Integer userId = smallTransactionRepository.convertUsernameToUserId(userName);

        if(smallDb.isPresent()) {
            SmallTransaction smallTransaction = smallDb.get();
            if(smallTransactionDTO.getItemName() != null) {smallTransaction.setItemName(smallTransactionDTO.getItemName());}
            if(smallTransactionDTO.getPrice() != null) {smallTransaction.setPrice(smallTransactionDTO.getPrice());}
            if(smallTransactionDTO.getTransactionTime() != null) {smallTransaction.setTransactionTime(smallTransactionDTO.getTransactionTime());}
            smallTransaction.setUserId(userId);
            smallTransactionRepository.save(smallTransaction);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Cập nhật thông tin thành công"));
        } else {
            return ResponseEntity.badRequest().body("Cập nhât thông tin không thành công");
        }
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
    public StatusSmallTransactionInRoomDTO getQuickStatus(Integer roomId, String username) {
        Integer userId = smallTransactionRepository.convertUsernameToUserId(username);
        BigDecimal mySpent = smallTransactionRepository.sumSpentByUserInRoom(userId, roomId);
        BigDecimal roomAverage = smallTransactionRepository.averageSpentInRoom(roomId);
        return new StatusSmallTransactionInRoomDTO(mySpent, roomAverage);
    }
}
