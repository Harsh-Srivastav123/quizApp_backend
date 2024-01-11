package com.example.quizapp.QuizApp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer session_Id;

    @OneToOne
    @JoinColumn(name="id")
    User creator;

    List<Integer> userList;
}
