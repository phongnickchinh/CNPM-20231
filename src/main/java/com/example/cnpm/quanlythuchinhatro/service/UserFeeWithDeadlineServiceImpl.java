package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.repository.UserFeeWithDeadlineRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class UserFeeWithDeadlineServiceImpl implements UserFeeWithDeadlineService{
    private final UserFeeWithDeadlineRepository userFeeWithDeadlineRepository;

    public UserFeeWithDeadlineServiceImpl(UserFeeWithDeadlineRepository userFeeWithDeadlineRepository) {
        this.userFeeWithDeadlineRepository = userFeeWithDeadlineRepository;
    }
    @Override
    public List<UserFeeWithDeadline> getStatusFeeWithDeadline(Integer id) {
        return userFeeWithDeadlineRepository.findAllById(id);
    }
}
