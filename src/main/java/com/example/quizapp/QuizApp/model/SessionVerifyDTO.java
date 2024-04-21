package com.example.quizapp.QuizApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Data

public class SessionVerifyDTO {


    Integer sessionId;
    String dateAndTime;
    Integer delayDuration;
    Integer duration;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Integer userId;
    String sessionTitle;
    List<QuestionDTO> sessionVerifyQuestionList;
    HashMap<Integer,String> userMapList;
}


