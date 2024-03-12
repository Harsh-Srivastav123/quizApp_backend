package com.example.quizapp.QuizApp.controller;

import com.example.quizapp.QuizApp.Services.PostService;
import com.example.quizapp.QuizApp.dao.PostDAO;
import com.example.quizapp.QuizApp.entity.Post;
import com.example.quizapp.QuizApp.entity.PostComments;
import com.example.quizapp.QuizApp.model.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;
    public record Message(Integer postId,String message){}
    @PostMapping("/createPost")
    public ResponseEntity<Message> createPost(@RequestBody PostDTO postDTO){
        PostDTO post1=postService.createPost(postDTO);
        return new ResponseEntity<>(new Message(post1.getPostId(),"Post created successfully"), HttpStatus.OK);
    }
    @GetMapping("/likePost")
    public ResponseEntity<String> likePost(@RequestParam(name = "userId",required = true) Integer userId,
                                           @RequestParam(name = "postId",required = true) Integer postId){
        String msg=postService.likePost(userId,postId);
        if(msg==null) return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }
    @GetMapping("/dislikePost")
    public ResponseEntity<String> dislikePost(@RequestParam(name = "userId",required = true) Integer userId,
                                           @RequestParam(name = "postId",required = true) Integer postId){

        String msg=postService.disLikePost(userId,postId);
        if(msg==null) return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<String> commentOnPost(@RequestBody PostComments postComments){
        postService.postComment(postComments);
        return new ResponseEntity<>("Successfully Commented on post",HttpStatus.OK);
    }




}
