package com.example.cnpm.quanlythuchinhatro.repository;

import com.example.cnpm.quanlythuchinhatro.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT r.id, r.roomName AS roomName, r.address, (CASE WHEN r.admin_id = m.userId THEN true ELSE false END) AS isAdmin " +
            "FROM MemberOfRoom m JOIN Room r ON r.id = m.roomId WHERE m.userId = (SELECT id FROM User WHERE username = :username)")
    List<Object[]> listRoom(String username);

}
