package com.example.websmartspending.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "JoinRoomReQuest")
public class JoinRoomReQuest {

    @EmbeddedId
    private JoinRoomReQuestIDEmbeddable id; // cặp khóa ngoại tạo thành khóa chính

    @ManyToOne
    @JoinColumn(name = "IDUser", insertable = false, updatable = false) // không cho phép cập nhật và chèn
    private User user;

    @ManyToOne
    @JoinColumn(name = "IDRoom", insertable = false, updatable = false)
    private Room room;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "RequestDate")
    private String requestDate;   
    
    @Column(name = "note")
    private String note;

    public JoinRoomReQuest() {
    }

    public JoinRoomReQuest(User user, Room room, Integer status) {
        this.user = user;
        this.room = room;
        this.status = status;
        this.id = new JoinRoomReQuestIDEmbeddable(user.getIdUser(), room.getIdRoom());
    }

    // getters and setters được tạo tự động bởi lombok


}
