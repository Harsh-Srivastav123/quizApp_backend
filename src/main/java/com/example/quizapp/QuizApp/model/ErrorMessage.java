package com.example.quizapp.QuizApp.model;

import com.example.quizapp.QuizApp.utils.CalculateDateTime;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@ToString
public class ErrorMessage {


     Date timeStamp;
     HttpStatus httpStatus;
     String message;


    public ErrorMessage(HttpStatus httpStatus, String message) {

        this.timeStamp=new Date();
        this.httpStatus = httpStatus;
        this.message = message;
    }


}
