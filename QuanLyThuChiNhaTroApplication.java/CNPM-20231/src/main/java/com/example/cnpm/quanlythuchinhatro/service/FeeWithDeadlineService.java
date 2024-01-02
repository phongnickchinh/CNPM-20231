package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;

import java.util.List;

public interface FeeWithDeadlineService {
    public void createFeeWithDeadline(FeeWithDeadline feeWithDeadline);
    public void deleteFeeWithDeadline(Integer id);
    public List<FeeWithDeadline> getAllFeeWithDeadline(Integer roomId);
    public FeeWithDeadline updateFeeWithDeadline(Integer id, FeeWithDeadline feeWithDeadline);
}
