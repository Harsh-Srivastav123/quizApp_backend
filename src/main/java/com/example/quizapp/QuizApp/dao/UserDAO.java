package com.example.quizapp.QuizApp.dao;

import com.example.quizapp.QuizApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Integer> {
}
