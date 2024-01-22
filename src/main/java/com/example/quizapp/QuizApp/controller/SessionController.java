package com.example.quizapp.QuizApp.controller;

import com.example.quizapp.QuizApp.Services.SessionService;
import com.example.quizapp.QuizApp.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    SessionService sessionService;
    public record Message(String sessionId,String message){}

    @PostMapping("/createSession")
    public ResponseEntity<Message> createSession(@RequestBody Session session){
        Session session1=sessionService.createSession(session);
        if(session!=null){
            return new ResponseEntity<>(new Message(session.getSessionId().toString(),"Session Created Successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message(null,"Unable to Create Session"),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("")
    public List<Session> sessionList(@RequestParam(value = "sessionId",defaultValue = "all",required = false) String sessionId){
        return sessionService.getSessionDetails(sessionId);
    }

}
