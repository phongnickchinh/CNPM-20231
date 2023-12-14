package com.example.websmartspending.service;

import com.example.websmartspending.model.FeeWithDeadline;
import com.example.websmartspending.model.FeeWithDeadlineIDEmbeddable;
import com.example.websmartspending.repository.FeeWithDeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeWithDeadlineServiceIpml implements FeeWithDeadlineService {
    private final FeeWithDeadlineRepository feeWithDeadlineRepository;

    public FeeWithDeadlineServiceIpml(FeeWithDeadlineRepository feeWithDeadlineRepository) {
        this.feeWithDeadlineRepository = feeWithDeadlineRepository;
    }
    @Override
    public FeeWithDeadline createFeeWithDeadline(FeeWithDeadline feeWithDeadline) {
        return feeWithDeadlineRepository.save(feeWithDeadline);
    }

    @Override
    public FeeWithDeadline updateFeeWithDeadline(Long id, FeeWithDeadline feeWithDeadline) {
        return null;
    }

    @Override
    public void deleteFeeWithDeadline(Long id) {
        feeWithDeadlineRepository.deleteById(id);
    }
}
