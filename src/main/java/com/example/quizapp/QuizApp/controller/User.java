package com.example.quizapp.QuizApp.controller;

import com.example.quizapp.QuizApp.Services.QuizService;
import com.example.quizapp.QuizApp.model.QuizResponse;
import com.example.quizapp.QuizApp.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class User {
    @Autowired
    QuizService quizService;
    @PostMapping("/response")
    public Result evaluateQuiz(@RequestBody QuizResponse quizResponse){
//        System.out.println("here");
//        if(quizResponse==null){
//            return false;
//        }
        return quizService.evaluateQuiz(quizResponse);
    }
    @GetMapping("/user/{id}")
    public com.example.quizapp.QuizApp.model.User getUser(@PathVariable Integer id){
        return quizService.getUser(id);
    }
    @GetMapping("/user")
    public List<com.example.quizapp.QuizApp.model.User> getALlUser(){
        List<com.example.quizapp.QuizApp.model.User> userList =quizService.getAllUser();
        Collections.sort(userList);
        return userList;
    }
}
