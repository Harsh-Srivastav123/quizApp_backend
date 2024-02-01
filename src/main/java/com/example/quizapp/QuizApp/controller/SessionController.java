package com.example.quizapp.QuizApp.controller;

import com.example.quizapp.QuizApp.Services.SessionService;
import com.example.quizapp.QuizApp.entity.Session;
import com.example.quizapp.QuizApp.model.Response;
import com.example.quizapp.QuizApp.model.SessionDTO;
import com.example.quizapp.QuizApp.model.SessionResponse;
import com.example.quizapp.QuizApp.model.SessionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    SessionService sessionService;
    public record Message(String sessionId,String message){}


    @PostMapping("/createSession")
    public ResponseEntity<Message> createSession(@RequestBody SessionDTO session){
        SessionDTO session1=sessionService.createSession(session);
        if(session1!=null){
            return new ResponseEntity<>(new Message(session1.getSessionId().toString(),"Session Created Successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message(null,"Unable to Create Session"),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("")
    public List<SessionDTO> sessionList(@RequestParam(value = "sessionId",defaultValue = "all",required = false) String sessionId){
        return sessionService.getSessionDetails(sessionId);
    }

    @PostMapping("/sessionResponse")
    public ResponseEntity<SessionResult> submitResponse(@RequestBody SessionResponse sessionResponse){
        SessionResult sessionResult=sessionService.submitResponse(sessionResponse);
        if(sessionResult!=null){
            return new ResponseEntity<>(sessionResult,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

}
