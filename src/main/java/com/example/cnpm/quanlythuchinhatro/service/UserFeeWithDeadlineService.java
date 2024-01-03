package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;

public interface UserFeeWithDeadlineService {
    UserFeeWithDeadline reverseStatus(Integer userId, Integer feeId);
}
