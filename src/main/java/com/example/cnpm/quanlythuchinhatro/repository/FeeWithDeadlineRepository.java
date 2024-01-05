package com.example.cnpm.quanlythuchinhatro.repository;

import com.example.cnpm.quanlythuchinhatro.dto.FeeWDStatusDTO;
import com.example.cnpm.quanlythuchinhatro.dto.StatusFeeWDRoomDTO;
import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface FeeWithDeadlineRepository extends JpaRepository<FeeWithDeadline, Integer> {
    List<FeeWithDeadline> findAllByRoomId(Integer roomId);
    @Query("SELECT id FROM User WHERE username = :username ")
    Integer convertUsernameToUserId (String username);


    @Query("""
    SELECT f.money / COUNT(DISTINCT mor.userId) AS pricePerUser
    FROM FeeWithDeadline f
    JOIN MemberOfRoom mor ON f.roomId = mor.roomId
    LEFT JOIN UserFeeWithDeadline userfee ON f.id = userfee.feeId
    WHERE f.roomId = :roomId AND userfee.userId = :userId
    GROUP BY f.feeName, f.deadline, f.status, f.money
    """)
    BigDecimal pricePerUser(@Param("userId") Integer userId, @Param("roomId") Integer roomId);

    @Query("""
    SELECT NEW com.example.cnpm.quanlythuchinhatro.dto.FeeWDStatusDTO(f.feeName, f.deadline, f.status, :pricePerUser)
    FROM FeeWithDeadline f
    WHERE f.roomId = :roomId AND EXISTS (
        SELECT 1 FROM UserFeeWithDeadline userfee
        WHERE f.id = userfee.feeId AND userfee.userId = :userId
    )
    GROUP BY f.feeName, f.deadline, f.status
    """)
    List<FeeWDStatusDTO> getFeeInfo(@Param("roomId") Integer roomId, @Param("userId") Integer userId, @Param("pricePerUser") BigDecimal pricePerUser);

    @Query(value = """
    SELECT
        f.id AS feeId,
        f.fee_name AS feeName,
        f.deadline AS deadline,
        f.money / (select COUNT(DISTINCT memb.user_id)
                    from member_of_room memb
                    where memb.room_id = :roomId
        ) AS pricePerUser,
        uf.user_id AS userId,
        COALESCE(uf.status, 0) AS payStatus,
        m.status AS inRoomStatus
    FROM
        fee_with_deadline f
    JOIN
        user_fee_with_deadline uf ON f.id = uf.fee_id
    JOIN
        member_of_room m ON m.user_id = uf.user_id AND m.room_id = f.room_id
    LEFT JOIN
        user_fee_with_deadline u ON f.id = u.fee_id AND u.user_id = uf.user_id
    WHERE
        f.room_id = :roomId
    GROUP BY
        f.id, f.fee_name, f.deadline, uf.user_id, uf.status, m.out_date, m.status;
            """, nativeQuery = true)
    List<Map<String,Object>> getList(@Param("roomId") Integer roomId);
    @Query(value = "SELECT * FROM fee_with_deadline WHERE room_id = :roomId AND EXTRACT(MONTH FROM deadline) = :month AND EXTRACT(YEAR FROM deadline) = :year", nativeQuery = true)
    List<FeeWithDeadline> findByRoomIdAndMonthAndYear(@Param("roomId") Integer roomId, @Param("month") Integer month, @Param("year") Integer year);


}
