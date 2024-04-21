package com.example.quizapp.QuizApp.controller;

import com.example.quizapp.QuizApp.Services.SessionService;
import com.example.quizapp.QuizApp.Services.UserService;
import com.example.quizapp.QuizApp.entity.Session;
import com.example.quizapp.QuizApp.model.*;
import com.example.quizapp.QuizApp.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    SessionService sessionService;
    public record Message(String sessionId,String message){}
    @Autowired
    UserService userService;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @PostMapping("/createSession")
    public ResponseEntity<Message> createSession(@RequestBody SessionDTO session){
        session.setUserId(userService.getUserByUserName(jwtAuthenticationFilter.getUserNameByToken()).getId());
        SessionDTO session1=sessionService.createSession(session);
        if(session1!=null){
            return new ResponseEntity<>(new Message(session1.getSessionId().toString(),"Session Created Successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message(null,"Unable to Create Session"),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<Object> getSessionDetails(@PathVariable Integer sessionId)
    {
        Object obj=sessionService.getSessionDetails(sessionId);
        if(obj.getClass()==SessionDTO.class){
            return new ResponseEntity<>(obj,HttpStatus.OK);
        }
     return new ResponseEntity<>(obj,HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/sessionResponse")
    public ResponseEntity<SessionResult> submitResponse(@RequestBody SessionResponse sessionResponse){
        sessionResponse.setUserId(userService.getUserByUserName(jwtAuthenticationFilter.getUserNameByToken()).getId());
        SessionResult sessionResult=sessionService.submitResponse(sessionResponse);
        if(sessionResult!=null){
            return new ResponseEntity<>(sessionResult,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("deleteSession/{sessionId}")
    public ResponseEntity<String> deleteSession(@PathVariable String sessionId){
        sessionService.deleteSession(Integer.parseInt(sessionId));
        return new ResponseEntity<>("Session deleted successfully sessionId = "+sessionId,HttpStatus.OK);
    }

    @PostMapping("/verifySession")
    public ResponseEntity<Object> getSessionVerify(@RequestBody SessionDTO sessionDTO){
        sessionDTO.setUserId(userService.getUserByUserName(jwtAuthenticationFilter.getUserNameByToken()).getId());
        return new ResponseEntity<>(sessionService.verifySession(sessionDTO),HttpStatus.OK);
    }

}
