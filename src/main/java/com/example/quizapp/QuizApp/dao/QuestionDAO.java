package com.example.quizapp.QuizApp.dao;

import com.example.quizapp.QuizApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionDAO extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);
}
