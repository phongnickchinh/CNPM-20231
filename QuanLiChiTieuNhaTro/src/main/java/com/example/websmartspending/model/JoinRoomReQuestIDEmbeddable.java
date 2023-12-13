package com.example.websmartspending.model;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class JoinRoomReQuestIDEmbeddable implements Serializable {

    private Integer userId;
    private Long roomId;

    // getters, setters, equals, and hashCode methods
    public JoinRoomReQuestIDEmbeddable(Integer userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }
}
