package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.repository.UserFeeWithDeadlineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserFeeWithDeadlineServiceImpl implements UserFeeWithDeadlineService{
    private final UserFeeWithDeadlineRepository userFeeWithDeadlineRepository;

    public UserFeeWithDeadlineServiceImpl(UserFeeWithDeadlineRepository userFeeWithDeadlineRepository){
        this.userFeeWithDeadlineRepository = userFeeWithDeadlineRepository;
    }
    @Override
    public List<Object[]> getStatusFeeWithDeadline(Integer roomId) {
        return userFeeWithDeadlineRepository.findByRoomId(roomId);
    }

    @Override
    public List<Object[]> getUserStatusFeeWithDeadline(Integer roomId, Integer userId) {
        return userFeeWithDeadlineRepository.findByUserId(roomId, userId);
    }

    @Override
    public boolean toggleFeeStatus(Integer userId, Integer feeId) {
        Optional<UserFeeWithDeadline> userFee = userFeeWithDeadlineRepository.findByUserIdAndFeeId(userId, feeId);
        if (userFee.isPresent()) {
            UserFeeWithDeadline fee = userFee.get();
            fee.setStatus(fee.getStatus() == 0 ? 1 : 0); // Đảo ngược trạng thái
            userFeeWithDeadlineRepository.save(fee);
            return true;
        }
        return false;
    }
}
