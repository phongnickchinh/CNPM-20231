package com.example.cnpm.quanlythuchinhatro.repository;

import com.example.cnpm.quanlythuchinhatro.model.SmallTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface SmallTransactionRepository extends JpaRepository<SmallTransaction, Integer> {
    @Query(value = """
    SELECT u.name AS fullname, sm.user_id AS userId, sm.transaction_time AS transactionTime, sm.room_id AS roomId, sm.price, sm.note, sm.item_name AS itemName, sm.id
    FROM small_transaction sm
    JOIN user u ON u.id = sm.user_id
    WHERE DATE_FORMAT(sm.transaction_time, '%Y-%m') = :yearMonth AND sm.room_id = :roomId
    """, nativeQuery = true)
    List<Map<String, Object>> getTransactionsByYearMonth(@Param("roomId") Integer roomId, @Param("yearMonth") String yearMonth);

    @Query(value = """

    SELECT u.name AS fullname, sm.user_id AS userId, sm.transaction_time AS transactionTime, sm.room_id AS roomId, sm.price, sm.note, sm.item_name AS itemName, sm.id
    FROM small_transaction sm
    JOIN user u ON u.id = sm.user_id
    WHERE DATE_FORMAT(sm.transaction_time, '%Y-%m') = :yearMonth AND sm.room_id = :roomId AND sm.user_id =:userId
    """, nativeQuery = true)
    List<Map<String, Object>> getTransactionsByYearMonthUserId(@Param("roomId") Integer roomId, @Param("userId") Integer userId, @Param("yearMonth") String datePrefix);

    @Query("SELECT id FROM User WHERE username = :username ")
    Integer convertUsernameToUserId (String username);
    @Query("SELECT SUM(st.price) FROM SmallTransaction st WHERE st.userId = :userId AND st.roomId = :roomId")
    BigDecimal sumSpentByUserInRoom(Integer userId, Integer roomId);
    @Query("SELECT (SUM(s.price)/(COUNT(DISTINCT s.userId))) AS averageRoom FROM SmallTransaction s WHERE s.roomId = :roomId")
    BigDecimal averageSpentInRoom(Integer roomId);
    @Query(value = "SELECT * FROM small_transaction WHERE room_id = :roomId AND EXTRACT(MONTH FROM transaction_time) = :month AND EXTRACT(YEAR FROM transaction_time) = :year", nativeQuery = true)
    List<SmallTransaction> findByRoomIdAndMonthAndYear(@Param("roomId") Integer roomId, @Param("month") Integer month, @Param("year") Integer year);

}
