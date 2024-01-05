package com.example.cnpm.quanlythuchinhatro.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cnpm.quanlythuchinhatro.dto.MemberSpending;
import com.example.cnpm.quanlythuchinhatro.dto.MonthlyStatsRequest;
import com.example.cnpm.quanlythuchinhatro.dto.MonthlyStatsResponse;
import com.example.cnpm.quanlythuchinhatro.dto.TransactionStatisticsResponse;
import com.example.cnpm.quanlythuchinhatro.model.MemberOfRoom;
import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;
import com.example.cnpm.quanlythuchinhatro.model.User;
import com.example.cnpm.quanlythuchinhatro.repository.MemberOfRoomRepository;
import com.example.cnpm.quanlythuchinhatro.repository.SmallTransactionRepository;
import com.example.cnpm.quanlythuchinhatro.repository.UserRepository;

@Service
public class TransactionService {

    @Autowired
    private SmallTransactionRepository smallTransactionRepository;

    @Autowired
    private MemberOfRoomRepository memberOfRoomRepository;

    @Autowired
    private UserRepository userRepository;

    public MonthlyStatsResponse getMonthlyStats(MonthlyStatsRequest request) {
        List<SmallTransaction> smallTransactions = smallTransactionRepository.findByRoomIdAndMonth(
                request.getRoomId(),
                getLastMonth()
        );

        List<MemberOfRoom> members = memberOfRoomRepository.findByRoomId(request.getRoomId());

        BigDecimal totalSpending = calculateTotalSpending(smallTransactions);
        BigDecimal averageSpending = calculateAverageSpending(totalSpending, members.size());
        List<MemberSpending> memberSpendings = calculateMemberSpendings(smallTransactions, members);

        MonthlyStatsResponse response = new MonthlyStatsResponse();
        response.setTotal(totalSpending);
        response.setAverage(averageSpending);
        response.setMemberSpendings(memberSpendings);

        return response;
    }

    private int getLastMonth() {
        return LocalDate.now().minus(1, ChronoUnit.MONTHS).getMonthValue();
    }

    private BigDecimal calculateTotalSpending(List<SmallTransaction> smallTransactions) {
        return smallTransactions.stream()
                .map(smallTransaction -> new BigDecimal(smallTransaction.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateAverageSpending(BigDecimal total, int memberCount) {
        if (memberCount > 0) {
            return total.divide(BigDecimal.valueOf(memberCount), 2, RoundingMode.HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }

    private List<MemberSpending> calculateMemberSpendings(List<SmallTransaction> smallTransactions, List<MemberOfRoom> members) {
        Map<Integer, BigDecimal> memberSpendingsMap = smallTransactions.stream()
                .collect(Collectors.groupingBy(
                        SmallTransaction::getUserId,
                        Collectors.mapping(smallTransaction -> new BigDecimal(smallTransaction.getPrice()), Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));

        return members.stream()
                .map(member -> {
                    MemberSpending memberSpending = new MemberSpending();
                    memberSpending.setUserId(member.getUserId());

                    Optional<User> userOptional = userRepository.findById(member.getUserId());
                    if (userOptional.isPresent()) {
                        User user = userOptional.get();
                        memberSpending.setFullname(user.getName());
                    } else {
                        memberSpending.setFullname("Unknown");
                    }

                    BigDecimal spending = memberSpendingsMap.getOrDefault(member.getUserId(), BigDecimal.ZERO);
                    memberSpending.setSpend(spending);

                    return memberSpending;
                })
                .collect(Collectors.toList());
    }
}
