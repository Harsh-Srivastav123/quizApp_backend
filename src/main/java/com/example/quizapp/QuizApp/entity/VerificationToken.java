package com.example.quizapp.QuizApp.entity;

import com.example.quizapp.QuizApp.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor

public class VerificationToken {

    private  static final int EXPIRATION_TIME=5;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer verificationId;
    String token;
    Date expirationTime;
    @OneToOne
    @JoinColumn(name = "id",nullable = false)
    User user;

    public VerificationToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime=calculateExpirationTime(EXPIRATION_TIME);
    }
    public VerificationToken(String token){
        super();
        this.token=token;
        this.expirationTime=calculateExpirationTime(EXPIRATION_TIME);
    }
    private Date calculateExpirationTime(int expirationTime) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
