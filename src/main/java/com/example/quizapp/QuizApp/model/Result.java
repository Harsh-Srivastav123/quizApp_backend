package com.example.quizapp.QuizApp.model;

import com.example.quizapp.QuizApp.utils.CalculateDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;


@Entity
@Component
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id_result;
    Integer totalQuestion;
    Integer totalAttemptQuestion;
    Integer nonAttemptQuestion;
    Integer rightAnswer;
    Integer wrongAnswer;
    String category;
    Integer totalMarks;
    String timeStamp ;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id")
    User user;



}

