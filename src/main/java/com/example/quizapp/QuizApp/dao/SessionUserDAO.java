package com.example.quizapp.QuizApp.dao;

import com.example.quizapp.QuizApp.entity.SessionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionUserDAO extends JpaRepository<SessionUser,String> {
}
