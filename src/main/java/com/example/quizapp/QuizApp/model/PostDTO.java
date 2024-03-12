package com.example.quizapp.QuizApp.model;

import com.example.quizapp.QuizApp.entity.PostComments;
import com.example.quizapp.QuizApp.entity.UserInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
@Data
public class PostDTO {
    Integer postId;
    String headLine;
    String description;
    List<UserInfo> likesUserId;
    List<UserInfo> dislikesUserId;
    List<PostComments> postCommentsList;
    String timeStamp;
}
