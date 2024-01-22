package com.example.quizapp.QuizApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.util.UUID;

@Entity
@Data
public class SessionUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String sessionUserId;
    Integer userId;
    String userName;
    Integer Marks;
    Integer sessionRank;
}
