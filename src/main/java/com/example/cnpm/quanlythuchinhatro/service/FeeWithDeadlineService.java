package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.FeeWDStatusDTO;
import com.example.cnpm.quanlythuchinhatro.dto.FeeWithDeadlineDTO;
import com.example.cnpm.quanlythuchinhatro.dto.ListFeeWDDTO;
import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FeeWithDeadlineService {
    ResponseEntity<?> createFeeWithDeadline(FeeWithDeadlineDTO feeWithDeadlineDTO);
    void deleteFeeWithDeadline(Integer id);
    List<ListFeeWDDTO> listFeeWDByRoomId(Integer roomId);
    FeeWithDeadline updateFeeWithDeadline(Integer id, FeeWithDeadlineDTO feeWithDeadlineDTO);
//    List<FeeWithDeadline> getFeeStatusByRoomId(Integer roomId);
    List<FeeWDStatusDTO> userStatusFeeWD(Integer roomId, String username);
}
