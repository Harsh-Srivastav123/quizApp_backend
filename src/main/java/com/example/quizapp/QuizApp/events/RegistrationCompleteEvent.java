package com.example.quizapp.QuizApp.events;

import com.example.quizapp.QuizApp.entity.User;
import com.example.quizapp.QuizApp.model.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {


    UserDTO user;
    String url;
    public RegistrationCompleteEvent(UserDTO user,String applicationUrl) {
        super(user);
        this.user=user;
        this.url=applicationUrl;
    }
}
