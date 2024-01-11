package com.example.quizapp.QuizApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Comparable<User> {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)

    Integer id;
    String userName;
    String profileUrl;
    int totalMarks;
    Integer userRank;
    Integer totalQuiz;
    @OneToMany(cascade =CascadeType.ALL,mappedBy = "user")
    List<Result> resultList;

    @Override
    public int compareTo(User o) {
        return o.totalMarks-this.totalMarks;
    }
}
