package com.example.quizapp.QuizApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer postId;
    String headLine;
    String description;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "likedUser",referencedColumnName = "postId")
    List<UserInfo> likesUserList;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dislikedUser",referencedColumnName = "postId")
    List<UserInfo> dislikesUserList;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post",referencedColumnName = "postId")
    List<PostComments> postCommentsList;
    String timeStamp;
}
