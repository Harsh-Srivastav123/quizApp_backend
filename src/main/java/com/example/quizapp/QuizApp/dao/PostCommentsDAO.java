package com.example.quizapp.QuizApp.dao;

import com.example.quizapp.QuizApp.entity.PostComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentsDAO extends JpaRepository<PostComments,Integer> {
}
