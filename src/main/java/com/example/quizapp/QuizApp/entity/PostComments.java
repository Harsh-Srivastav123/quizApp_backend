package com.example.quizapp.QuizApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PostComments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer postCommentId;
    Integer postId;
    String description;
    String timeStamp;

}
