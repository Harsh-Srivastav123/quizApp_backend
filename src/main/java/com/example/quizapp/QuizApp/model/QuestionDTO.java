package com.example.quizapp.QuizApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionDTO {

    Integer id;
    String question;
    String options1;
    String options2;
    String options3;
    String options4;

    @JsonProperty(  access = JsonProperty.Access.WRITE_ONLY)
    String rightAnswer;
    String category;
    String topic;
    String marks;
    String difficulty;

    public QuestionDTO(Integer id) {
        this.id = id;
    }
}
