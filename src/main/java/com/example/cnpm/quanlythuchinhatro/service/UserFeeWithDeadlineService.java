package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;

import java.util.List;

public interface UserFeeWithDeadlineService {
    public List<Object[]> getStatusFeeWithDeadline(Integer roomId);
}
