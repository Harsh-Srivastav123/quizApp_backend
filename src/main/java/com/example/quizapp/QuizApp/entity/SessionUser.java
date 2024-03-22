package com.example.quizapp.QuizApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.util.UUID;

@Entity
@Data
public class SessionUser implements Comparable<SessionUser> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String sessionUserId;
    Integer userId;
    String userName;
    int Marks;
    Integer sessionRank;
    boolean isCompleted;
    String submissionTimeStamp;

    @Override
    public int compareTo(SessionUser sessionUser){
        return sessionUser.getMarks()-this.getMarks();
    }
}
