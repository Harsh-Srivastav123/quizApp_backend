package com.example.quizapp.QuizApp.exceptions;

public class QuestionNotFound extends Exception{

    public QuestionNotFound() {
    }

    public QuestionNotFound(String message) {
        super(message);
    }

    public QuestionNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestionNotFound(Throwable cause) {
        super(cause);
    }

    public QuestionNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
