package com.example.quizapp.QuizApp.model;

import com.example.quizapp.QuizApp.entity.SessionUser;
import lombok.Data;

import java.util.List;
@Data
public class SessionDTO {
    Integer sessionId;
    String dateAndTime;
    Integer delayDuration;
    Integer duration;
    Integer userId;
    String sessionTitle;
    List<QuestionDTO> sessionQuestionList;
    List<SessionUser> sessionUserList;
}
