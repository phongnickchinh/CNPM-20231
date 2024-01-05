package com.example.cnpm.quanlythuchinhatro.service;

import com.example.cnpm.quanlythuchinhatro.dto.RoomExpenseDTO;
import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.model.Room;
import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;
import com.example.cnpm.quanlythuchinhatro.repository.FeeWithDeadlineRepository;
import com.example.cnpm.quanlythuchinhatro.repository.MemberOfRoomRepository;
import com.example.cnpm.quanlythuchinhatro.repository.RoomRepository;
import com.example.cnpm.quanlythuchinhatro.repository.SmallTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomExpenseServiceImpl implements RoomExpenseService{
    @Autowired
    private FeeWithDeadlineRepository feeWithDeadlineRepository;

    @Autowired
    private SmallTransactionRepository smallTransactionRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberOfRoomRepository memberOfRoomRepository;

    @Override
    public List<RoomExpenseDTO> calculateMonthlyExpenses(int month, int year) {
        List<RoomExpenseDTO> roomExpenses = new ArrayList<>();
        List<Room> rooms = roomRepository.findAll();

        for (Room room : rooms) {
            BigDecimal totalSpending = calculateTotalSpendingForRoom(room.getId(), month, year);
            RoomExpenseDTO dto = new RoomExpenseDTO();
            dto.setRoomName(room.getRoomName());
            dto.setTotalSpending(totalSpending);
            roomExpenses.add(dto);
        }

        return roomExpenses;
    }

    private BigDecimal calculateTotalSpendingForRoom(Integer roomId, int month, int year) {
        List<FeeWithDeadline> fees = feeWithDeadlineRepository.findByRoomIdAndMonthAndYear(roomId, month, year);
        List<SmallTransaction> transactions = smallTransactionRepository.findByRoomIdAndMonthAndYear(roomId, month, year);

        BigDecimal totalFee = fees.stream().map(FeeWithDeadline::getMoney).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalTransaction = transactions.stream().map(transaction -> new BigDecimal(transaction.getPrice())).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSpending = totalFee.add(totalTransaction);

        int numberOfPeopleInRoom = memberOfRoomRepository.countMember(roomId);
        if (numberOfPeopleInRoom == 0) {
            return BigDecimal.ZERO;
        }

        return totalSpending.divide(BigDecimal.valueOf(numberOfPeopleInRoom), RoundingMode.HALF_UP);
    }

}
