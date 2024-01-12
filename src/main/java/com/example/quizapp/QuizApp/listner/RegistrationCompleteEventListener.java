package com.example.quizapp.QuizApp.listner;

import com.example.quizapp.QuizApp.Services.MailSenderService;
import com.example.quizapp.QuizApp.Services.UserService;
import com.example.quizapp.QuizApp.events.RegistrationCompleteEvent;
import com.example.quizapp.QuizApp.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {


    @Autowired
    UserService userService;
    @Autowired
    MailSenderService mailSenderService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user=event.getUser();
        String token= UUID.randomUUID().toString();
        userService.saveVerificationToken(user,token);
        String url=event.getUrl().toString()+"/user/verifyRegistration?token="+token;
        log.info("click here to verify account   "+ url);
        mailSenderService.sendMail(user.getEmail(),url);
    }
}
