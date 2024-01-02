package com.example.cnpm.quanlythuchinhatro.repository;

import com.example.cnpm.quanlythuchinhatro.model.JoinRoomRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JoinRoomRequestRepository extends JpaRepository<JoinRoomRequest, Long> {
    // Phương thức truy vấn để tìm JoinRoomRequest theo userId và roomId
    Optional<JoinRoomRequest> findByUserIdAndRoomId(Integer userId, Integer roomId);
}
