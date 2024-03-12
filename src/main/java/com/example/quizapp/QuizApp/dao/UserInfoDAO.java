package com.example.quizapp.QuizApp.dao;

import com.example.quizapp.QuizApp.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoDAO extends JpaRepository<UserInfo,Integer> {

}
