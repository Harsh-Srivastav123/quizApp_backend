package com.example.quizapp.QuizApp.model;

import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class JwtResponse {
    Integer userId;
    String token;
    String userName;
}
