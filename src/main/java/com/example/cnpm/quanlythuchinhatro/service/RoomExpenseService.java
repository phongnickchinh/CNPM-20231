package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.RoomExpenseDTO;

import java.util.List;

public interface RoomExpenseService {

    List<RoomExpenseDTO> calculateMonthlyExpenses(int month, int year);

}
