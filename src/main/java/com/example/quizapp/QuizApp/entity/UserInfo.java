package com.example.quizapp.QuizApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.convert.DataSizeUnit;

@Entity
@Data
@NoArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer userInfoId;
    Integer userId;
    String userName;


    public UserInfo(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
