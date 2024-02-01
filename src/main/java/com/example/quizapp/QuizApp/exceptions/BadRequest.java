package com.example.quizapp.QuizApp.exceptions;

public class BadRequest extends RuntimeException{
    public BadRequest() {
        super();
    }

    public BadRequest(String message) {
        super(message);
    }
}
