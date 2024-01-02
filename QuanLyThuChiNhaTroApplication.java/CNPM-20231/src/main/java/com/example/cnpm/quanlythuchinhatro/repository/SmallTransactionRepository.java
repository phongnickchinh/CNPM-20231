package com.example.cnpm.quanlythuchinhatro.repository;

import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SmallTransactionRepository extends JpaRepository<SmallTransaction, Integer> {
    List<SmallTransaction> findByRoomIdAndTransactionTimeStartingWith(Integer roomId, String datePrefix);
    List<SmallTransaction> findByRoomIdAndUserIdAndTransactionTimeStartingWith(Integer roomId, Integer userId, String datePrefix);

    @Query("SELECT SUM(st.price) FROM SmallTransaction st WHERE st.userId = :userId AND st.roomId = :roomId")
    BigDecimal sumSpentByUserInRoom(Integer userId, Integer roomId);
    @Query("SELECT (SUM(s.price)/(COUNT(DISTINCT s.userId))) AS averageRoom FROM SmallTransaction s WHERE s.roomId = :roomId")
    BigDecimal averageSpentInRoom(Integer roomId);
}
