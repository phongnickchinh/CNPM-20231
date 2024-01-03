package com.example.cnpm.quanlythuchinhatro.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.cnpm.quanlythuchinhatro.model.JoinRoomRequest;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

@Repository
public interface JoinRoomRequestRepository extends JpaRepository<JoinRoomRequest, Long> {

    @Query("SELECT j.requestDate AS requestDate, j.userId AS userId, u.name AS fullName, u.avatarUrl AS avatarUrl FROM JoinRoomRequest j JOIN User u ON j.userId = u.id WHERE j.roomId =:roomId")
    List<Object[]> getJRRForAdmin(Integer roomId);

    // Phương thức truy vấn để tìm JoinRoomRequest theo userId và roomId
    Optional<JoinRoomRequest> findByUserIdAndRoomId(Integer userId, Integer roomId);
}
