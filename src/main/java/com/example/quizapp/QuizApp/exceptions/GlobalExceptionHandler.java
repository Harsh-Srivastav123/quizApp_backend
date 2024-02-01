package com.example.quizapp.QuizApp.exceptions;

import com.example.quizapp.QuizApp.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@ResponseStatus
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessage> customException(CustomException exception,
                                                                    WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,
                exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }
    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ErrorMessage> badRequest(BadRequest exception,
                                                                    WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,
                exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleRootException(Exception exception) {
//        log.error(
//                ">>> global error message {} and cause {} ",
//                exception.getMessage(),
//                exception.getCause());
//        String responseBody =
//                new ErrorMessage(HttpStatus.BAD_REQUEST,"error found request failed").toString();
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(responseBody);
//    }
}
