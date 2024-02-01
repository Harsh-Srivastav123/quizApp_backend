package com.example.quizapp.QuizApp.model;

import lombok.Data;

@Data
public class CategoryData {
    String category;
    int totalQuestion;
    int easyQuestion;
    int mediumQuestion;
    int hardQuestion;
}
