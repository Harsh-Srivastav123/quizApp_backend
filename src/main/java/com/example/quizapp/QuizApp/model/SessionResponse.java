package com.example.quizapp.QuizApp.model;

import lombok.Data;

import java.util.List;
@Data
public class SessionResponse {
    List<Response> responseList;
    Integer sessionId;
    Integer userId;
    String sessionUserId;
}
