package com.example.quizapp.QuizApp.dao;

import com.example.quizapp.QuizApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User,Integer> {
    User findByUserName(String userName);
}
