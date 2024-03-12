package com.example.quizapp.QuizApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class SessionResponse {
    List<Response> responseList;
    Integer sessionId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Integer userId;
    String sessionUserId;
}




