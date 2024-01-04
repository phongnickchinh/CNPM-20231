package com.example.cnpm.quanlythuchinhatro.repository;

import com.example.cnpm.quanlythuchinhatro.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query(value = """
            SELECT r.id, r.name, r.address, (CASE WHEN r.admin_id = m.user_id THEN r.admin_id ELSE null END) AS admin_id
            FROM member_of_room m JOIN room r ON r.id = m.room_id WHERE m.user_id = (SELECT id FROM user WHERE username = :username) AND m.status = 1
            """, nativeQuery = true)
    List<Room> listRoom(String username);

    @Query(value = """
            SELECT r.admin_id
            FROM room r
            WHERE r.id = :roomId
            """, nativeQuery = true)
    Integer getAdminId(Integer roomId);

}
