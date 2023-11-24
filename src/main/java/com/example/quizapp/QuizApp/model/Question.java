package com.example.quizapp.QuizApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Component
@NoArgsConstructor
@Data
@Getter
@Setter
@AllArgsConstructor
@Table(name = "quiz")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
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

    public Question(String question, String options1, String options2, String options3, String options4, String rightAnswer, String topic) {
        this.question = question;
        this.options1 = options1;
        this.options2 = options2;
        this.options3 = options3;
        this.options4 = options4;
        this.rightAnswer = rightAnswer;
        this.category = category;
        this.topic=topic;
    }


}
