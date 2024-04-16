package com.example.quizapp.QuizApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer sessionId;

    String dateAndTime ;

//    @JsonIgnore
    Date startTimeStamp;

//    @JsonIgnore
    Date expiryTimeStamp;

    Integer delayDuration;

    Integer duration;

    @Column(name = "creator")
    Integer userId;

    String sessionTitle;

    @ManyToMany(cascade =CascadeType.MERGE)

    @JoinTable(
            name ="session_question_mapping",
            joinColumns = @JoinColumn(
                    name = "sessionId",
                    referencedColumnName = "sessionId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "questionId",
                    referencedColumnName = "id"
            )
    )
    List<Question> sessionQuestionList;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sessionId",referencedColumnName = "sessionId")
    List<SessionUser> sessionUserList;
}
