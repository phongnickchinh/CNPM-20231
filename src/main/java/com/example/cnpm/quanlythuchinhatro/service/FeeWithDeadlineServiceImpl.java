package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.repository.FeeWithDeadlineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeWithDeadlineServiceImpl implements FeeWithDeadlineService{
    private final FeeWithDeadlineRepository feeWithDeadlineRepository;
    public FeeWithDeadlineServiceImpl(FeeWithDeadlineRepository feeWithDeadlineRepository) {
        this.feeWithDeadlineRepository = feeWithDeadlineRepository;
    }


    @Override
    public void createFeeWithDeadline(FeeWithDeadline feeWithDeadline) {
        feeWithDeadlineRepository.save(feeWithDeadline);
    }

    @Override
    public void deleteFeeWithDeadline(Integer id) {
        feeWithDeadlineRepository.deleteById(id);
    }

    @Override
    public List<FeeWithDeadline> getAllFeeWithDeadline(Integer roomId) {
        return feeWithDeadlineRepository.findAllByRoomId(roomId);
    }

    @Override
    public FeeWithDeadline updateFeeWithDeadline(Integer id, FeeWithDeadline feeWithDeadline) {
        feeWithDeadline.setId(id);
        feeWithDeadlineRepository.save(feeWithDeadline);
        return feeWithDeadline;
    }

}
