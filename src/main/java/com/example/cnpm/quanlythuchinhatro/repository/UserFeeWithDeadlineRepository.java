package com.example.cnpm.quanlythuchinhatro.repository;

import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserFeeWithDeadlineRepository extends JpaRepository<UserFeeWithDeadline, Long> {
    @Query("SELECT u.userId AS userId, u.feeId AS feeId, f.feeName AS feeName, f.deadline AS deadline, u.status AS status " +
            ", f.money / ((SELECT COUNT(*) FROM UserFeeWithDeadline WHERE roomId = : roomId AND feeId = u.feeId )) AS pricePerUser " +
            "FROM UserFeeWithDeadline u " +
            "JOIN FeeWithDeadline f ON u.feeId = f.id " +
            "WHERE u.roomId = :roomId " +
            "GROUP BY u.userId, u.feeId, f.feeName, f.deadline, f.money, u.status ")
    List<Object[]> findByRoomId(@Param("roomId") Integer roomId);

    Optional<UserFeeWithDeadline> findByUserIdAndFeeId(Integer userId, Integer feeId);

    @Query("SELECT f.feeName AS feeName, f.deadline AS deadline, u.status AS statusFee, " +
            "f.money / ((SELECT COUNT(ufwd) FROM UserFeeWithDeadline ufwd WHERE ufwd.roomId = :roomId AND ufwd.feeId = f.id)) AS pricePerUser " +
            "FROM UserFeeWithDeadline u " +
            "JOIN FeeWithDeadline f ON u.feeId = f.id " +
            "WHERE u.userId = :userId AND u.roomId = :roomId " +
            "GROUP BY u.userId, u.feeId, f.feeName, f.deadline, f.money, u.status")
    List<Object[]> findByUserId(@Param("roomId") Integer roomId, @Param("userId") Integer userId);



}
