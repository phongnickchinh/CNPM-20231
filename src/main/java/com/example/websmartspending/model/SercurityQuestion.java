package com.example.websmartspending.model;
import javax.persistence.*;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

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