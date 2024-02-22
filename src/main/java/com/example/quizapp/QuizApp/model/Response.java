package com.example.quizapp.QuizApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    int id;
    String rightAnswer;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String submitResponse;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    boolean result;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String question;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Integer marks;

    public Response(int id, String rightAnswer) {
        this.id = id;
        this.rightAnswer = rightAnswer;
    }
}
