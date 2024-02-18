package com.example.quizapp.QuizApp.model;

import com.example.quizapp.QuizApp.entity.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailResult {
    List<Response> responseList;
    Result result;
}
