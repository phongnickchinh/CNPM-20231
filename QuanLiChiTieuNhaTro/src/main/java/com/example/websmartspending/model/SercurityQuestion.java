package com.example.websmartspending.model;
import javax.persistence.*;
import lombok.Data;
// hoàn thành
@Entity
@Data
@Table(name = "SercurityQuestion")
public class SercurityQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSercurityQuestion")
    private Integer idSercurityQuestion;

    @ManyToOne
    @JoinColumn(name = "IDUser")
    private User user;

    @Column(name = "Question")
    private String question;

    @Column(name = "Answer")
    private String answer;

    public SercurityQuestion() {
    }

    public SercurityQuestion(String question, String answer, User user) {
        this.question = question;
        this.answer = answer;
        this.user = user;
    }
}