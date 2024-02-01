package com.example.quizapp.QuizApp.entity;

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
@ToString

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    Integer marks;
    String difficulty;
    boolean isEnabled;
}
