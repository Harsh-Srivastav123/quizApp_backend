package com.example.quizapp.QuizApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionList {
    List<Question> questionList;
    int totalQuestion;
    String category;
}
