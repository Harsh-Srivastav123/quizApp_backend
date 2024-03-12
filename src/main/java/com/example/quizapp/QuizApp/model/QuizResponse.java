package com.example.quizapp.QuizApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class QuizResponse {
    List<Response> responseList;
    Integer totalQuestion;
    Integer maximumMarks;
    String category;
    @JsonIgnore
    Integer userId;

}
