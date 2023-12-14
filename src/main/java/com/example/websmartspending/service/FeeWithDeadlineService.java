package com.example.websmartspending.service;

import com.example.websmartspending.model.FeeWithDeadline;

public interface FeeWithDeadlineService {
    public FeeWithDeadline createFeeWithDeadline(FeeWithDeadline feeWithDeadline);
    public FeeWithDeadline updateFeeWithDeadline(Long id, FeeWithDeadline feeWithDeadline);
    public void deleteFeeWithDeadline(Long id);
}
