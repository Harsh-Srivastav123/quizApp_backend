package com.example.quizapp.QuizApp.dao;

import com.example.quizapp.QuizApp.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface QuestionDAO extends JpaRepository<Question, Integer> {

    Page<Question> findByCategory(String category, Pageable pageable);
    List<Question> findByDifficulty(String difficulty);

    @Query(value = "SELECT COUNT(id) FROM Question WHERE category= :n",nativeQuery = true)
    Integer categorySize(@Param("n")  String category);
}
