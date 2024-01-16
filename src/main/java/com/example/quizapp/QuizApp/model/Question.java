package com.example.quizapp.QuizApp.model;

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
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String question;
    String options1;
    String options2;
    String options3;
    String options4;
    String rightAnswer;
    String category;
    String topic;

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
