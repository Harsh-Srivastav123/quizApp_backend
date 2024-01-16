package com.example.quizapp.QuizApp.model;

import com.example.quizapp.QuizApp.utils.CalculateDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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

    Date currTimeStamp;

    Integer duration;

    @Column(name = "creator")
    Integer userId;



    @ManyToMany(cascade = CascadeType.ALL)
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
