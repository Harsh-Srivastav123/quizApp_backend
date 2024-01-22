package com.example.quizapp.QuizApp.events;

import com.example.quizapp.QuizApp.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;


@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {


    User user;
    String url;
    public RegistrationCompleteEvent(User user,String applicationUrl) {
        super(user);
        this.user=user;
        this.url=applicationUrl;
    }
}
