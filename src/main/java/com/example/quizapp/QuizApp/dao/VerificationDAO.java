package com.example.quizapp.QuizApp.dao;

import com.example.quizapp.QuizApp.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationDAO extends JpaRepository<VerificationToken,Integer> {
    VerificationToken findByToken(String token);

}
