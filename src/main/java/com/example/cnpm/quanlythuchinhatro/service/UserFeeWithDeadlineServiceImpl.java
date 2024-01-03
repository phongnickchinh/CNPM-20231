package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.repository.UserFeeWithDeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserFeeWithDeadlineServiceImpl implements UserFeeWithDeadlineService{
    @Autowired
    private UserFeeWithDeadlineRepository feeWithDeadlineRepository;

    public UserFeeWithDeadline reverseStatus(Integer userId, Integer feeId) {
        Optional<UserFeeWithDeadline> userFeeWithDeadlineOptional = feeWithDeadlineRepository.findByUserIdAndFeeId(userId, feeId);

        if (userFeeWithDeadlineOptional.isPresent()) {
            UserFeeWithDeadline userFeeWithDeadline = userFeeWithDeadlineOptional.get();
            userFeeWithDeadline.setStatus(userFeeWithDeadline.getStatus() == 1 ? 0 : 1);

            feeWithDeadlineRepository.save(userFeeWithDeadline);

            return userFeeWithDeadline;
        } else {
            return null;
        }
    }
}
