package com.example.cnpm.quanlythuchinhatro.repository;

import com.example.cnpm.quanlythuchinhatro.dto.FeeWDStatusDTO;
import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

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


}
