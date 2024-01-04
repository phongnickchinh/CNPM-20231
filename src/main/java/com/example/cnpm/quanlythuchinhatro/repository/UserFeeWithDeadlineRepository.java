package com.example.cnpm.quanlythuchinhatro.repository;

import com.example.cnpm.quanlythuchinhatro.model.UserFeeWithDeadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserFeeWithDeadlineRepository extends JpaRepository<UserFeeWithDeadline, Long> {
    Optional<UserFeeWithDeadline> findByUserIdAndFeeId(Integer userId, Integer feeId);
}
