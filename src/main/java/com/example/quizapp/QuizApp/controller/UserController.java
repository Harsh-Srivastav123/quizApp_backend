package com.example.quizapp.QuizApp.controller;

import com.example.quizapp.QuizApp.Services.CloudinaryService;
import com.example.quizapp.QuizApp.Services.QuizService;
import com.example.quizapp.QuizApp.model.QuizResponse;
import com.example.quizapp.QuizApp.model.Result;
import com.example.quizapp.QuizApp.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@RestController
@RequestMapping("/quiz")
public class UserController {
    @Autowired
    QuizService quizService;

    @Autowired
    CloudinaryService cloudinaryService;
    @PostMapping("/response")
    public Result evaluateQuiz(@RequestBody QuizResponse quizResponse){
        if(quizResponse==null){
            return null;
        }
        return quizService.evaluateQuiz(quizResponse);
    }
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id){
        return quizService.getUser(id);
    }
    @GetMapping("/user")
    public List<User> getALlUser(){
        List<com.example.quizapp.QuizApp.model.User> userList =quizService.getAllUser();
        Collections.sort(userList);
        return userList;
    }

    //create user
    @PostMapping(path="/createUser", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })

    public ResponseEntity<User>  createUser(
            @RequestParam("user") String user,
            @RequestPart("image")MultipartFile file){
        Map data= new HashMap();
        User user1;
        // String to User
        ObjectMapper mapper=new ObjectMapper();
        try {
            user1 = mapper.readValue(user, User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if(file!=null){
            data=cloudinaryService.upload(file);
            System.out.println(data);
        }
        if(user==null){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
        }
        else {
            user1.setProfileUrl(data.get("url").toString());
        }
        return new ResponseEntity<>(quizService.createUser(user1),HttpStatusCode.valueOf(200));
    }
}
