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

    @Query("SELECT st FROM SmallTransaction st WHERE st.roomId = :roomId AND FUNCTION('MONTH', FUNCTION('DATE_FORMAT', st.transactionTime, '%Y-%m-%dT%H:%i:%s.%fZ')) = :month")
    List<SmallTransaction> findByRoomIdAndMonth(@Param("roomId") Integer roomId, @Param("month") Integer month);
//    @Query("SELECT SUM(st.price) FROM SmallTransaction st WHERE st.userId = :userId AND st.roomId = :roomId")
//    BigDecimal sumSpentByUserInRoom(Integer userId, Integer roomId);
//    @Query("SELECT (SUM(s.price)/(COUNT(DISTINCT s.userId))) AS averageRoom FROM SmallTransaction s WHERE s.roomId = :roomId")
//    BigDecimal averageSpentInRoom(Integer roomId);

    @Query(value = """
   SELECT
        SUM(s.price) AS sumUser
    FROM
        small_transaction s\s
    WHERE
        s.room_id = :roomId
        AND s.user_id = :userId
        AND DATE_FORMAT(s.transaction_time, '%Y-%m') = DATE_FORMAT(CURRENT_DATE(), '%Y-%m')

    """, nativeQuery = true)
    BigDecimal sumSpentByUserInRoom(Integer userId, Integer roomId);

    @Query(value = """
    SELECT\s
        SUM(s.price) / COUNT(DISTINCT s.user_id) AS averageRoom
    FROM\s
        small_transaction s\s
    WHERE\s
        DATE_FORMAT(s.transaction_time, '%Y-%m') = DATE_FORMAT(CURRENT_DATE(), '%Y-%m')
        AND s.room_id = :roomId
    """,nativeQuery = true)
    BigDecimal averageSpentInRoom(Integer roomId);

}
