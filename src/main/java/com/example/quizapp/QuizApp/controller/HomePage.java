
package com.example.quizapp.QuizApp.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomePage {
    @GetMapping("/")
    public String homePage(){
        return "index";
    }
}
