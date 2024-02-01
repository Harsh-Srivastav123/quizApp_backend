package com.example.quizapp.QuizApp.model;

import com.example.quizapp.QuizApp.entity.Result;
import com.example.quizapp.QuizApp.entity.Session;
import com.example.quizapp.QuizApp.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class UserDTO implements Comparable<UserDTO>{

    Integer Id;
    String userName;
    String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    String profileUrl;
    int totalMarks;
    int userRank;
    int totalQuiz;

    List<Result> resultList;

    List<Session> sessionList;

    @Override
    public int compareTo(UserDTO o) {
        return this.userRank-o.userRank;
    }

}
