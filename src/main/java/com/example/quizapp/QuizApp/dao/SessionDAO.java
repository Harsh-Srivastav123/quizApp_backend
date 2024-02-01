package com.example.quizapp.QuizApp.dao;

import com.example.quizapp.QuizApp.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionDAO extends JpaRepository<Session, Integer> {
}
