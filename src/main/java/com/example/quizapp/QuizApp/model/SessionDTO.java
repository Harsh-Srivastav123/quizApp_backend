package com.example.quizapp.QuizApp.model;

import com.example.quizapp.QuizApp.entity.SessionUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class SessionDTO {
    Integer sessionId;
    String dateAndTime;
    Integer delayDuration;
    Integer duration;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Integer userId;
    String sessionTitle;
    List<QuestionDTO> sessionQuestionList;
    List<SessionUser> sessionUserList;
}
