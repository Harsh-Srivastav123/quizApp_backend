package com.example.quizapp.QuizApp.model;


import lombok.Data;

@Data
public class SessionResult {
    String message;
    int TotalQuestion;
    int correct;
    int wrong;
    String sessionUserId;
    Integer userId;
    Integer sessionId;
    int marks;
}
