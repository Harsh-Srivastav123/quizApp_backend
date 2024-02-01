package com.example.quizapp.QuizApp.dao;

import com.example.quizapp.QuizApp.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionDAO extends JpaRepository<Question, Integer> {

    Page<Question> findByCategory(String category, Pageable pageable);
    List<Question> findByDifficulty(String difficulty);

    @Query(value = "SELECT COUNT(id) FROM question WHERE category= :n",nativeQuery = true)
    Integer categorySize(@Param("n")  String category);

    @Query(value = "SELECT COUNT(id) FROM question WHERE category= :category AND difficulty= :n",nativeQuery = true)
    Integer categoryWithDifficulty(@Param("category") String category , @Param("n")  String difficulty);

    @Query(value = "SELECT DISTINCT(category) FROM question",nativeQuery = true)
    List<String> allCategory();


    boolean existsByCategory(String category);
}
