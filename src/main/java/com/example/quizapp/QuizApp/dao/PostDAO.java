package com.example.quizapp.QuizApp.dao;

import com.example.quizapp.QuizApp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDAO extends JpaRepository<Post,Integer> {
}
