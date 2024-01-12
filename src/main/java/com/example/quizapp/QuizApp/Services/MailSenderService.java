package com.example.quizapp.QuizApp.Services;


import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Value("$(Quiz-App)")
    String from;
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMail(String mail,String message){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject("Verify Email to login to Quiz-App");
        simpleMailMessage.setText("verify your email  "+message);
        simpleMailMessage.setTo(mail);
        javaMailSender.send(simpleMailMessage);
    }
}
