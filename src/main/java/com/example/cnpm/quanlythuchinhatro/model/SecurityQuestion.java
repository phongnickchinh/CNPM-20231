package com.example.cnpm.quanlythuchinhatro.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "SecurityQuestion")
public class SecurityQuestion {
    //id
    //user_id
    //question
    //answer

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id")
    
    private Integer id;

    @Column(name="user_id")
    private Integer userId;
    
    @Column(name = "question")
    private String question;
    
    @Column (name= "answer")
    private String answer;
    
    public SecurityQuestion() {
    }
    
    public SecurityQuestion(Integer userId, String question, String answer) {
        this.userId = userId;
        this.question=question;
        this.answer=answer;
    }
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}