package com.example.cnpm.quanlythuchinhatro.repository;


import com.example.cnpm.quanlythuchinhatro.model.FeeWithDeadline;
import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserFeeWithDeadlineRepository extends JpaRepository<UserFeeWithDeadline, Integer> {
    //Native query
    @Query(value = "SELECT u.fee_id, u.user_id, u.status_fee, f.fee_name, f.money FROM user_fee_with_deadline u " +
            "INNER JOIN fee_with_deadline f ON u.fee_id = f.id " +
            "WHERE f.room_id = :roomId", nativeQuery = true)
    List<UserFeeWithDeadline> findByFeeWithDeadline_RoomId(Integer roomId);
}
