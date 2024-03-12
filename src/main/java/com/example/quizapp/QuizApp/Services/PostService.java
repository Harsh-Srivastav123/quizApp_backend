package com.example.quizapp.QuizApp.Services;

import com.example.quizapp.QuizApp.dao.PostCommentsDAO;
import com.example.quizapp.QuizApp.dao.PostDAO;
import com.example.quizapp.QuizApp.dao.UserDAO;
import com.example.quizapp.QuizApp.entity.Post;
import com.example.quizapp.QuizApp.entity.PostComments;
import com.example.quizapp.QuizApp.entity.UserInfo;
import com.example.quizapp.QuizApp.exceptions.BadRequest;
import com.example.quizapp.QuizApp.model.PostDTO;
import com.example.quizapp.QuizApp.utils.CalculateDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.prefs.BackingStoreException;

@Service
public class PostService {
    @Autowired
    PostDAO postDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CalculateDateTime timeStamp;
    @Autowired
    PostCommentsDAO postCommentsDAO;

    public PostDTO createPost(PostDTO post) {
        if (post.getPostId() != null && postDAO.existsById(post.getPostId())) {
            return null;
        }
        post.setTimeStamp(timeStamp.calculateDateTime());
        return modelMapper.map(postDAO.save(modelMapper.map(post, Post.class)), PostDTO.class);
    }

    public String likePost(Integer userId, Integer postId) {
        if (!postDAO.existsById(postId)) {
            throw new BadRequest("Post doesn't exist");
        }
        if (!userDAO.existsById(userId)) {
            throw new BadRequest("User doesn't exist");
        }
        List<UserInfo> likeList = postDAO.findById(postId).get().getLikesUserList();
        for (UserInfo userInfo : likeList) {
            if (userInfo.getUserId().intValue() == userId.intValue()) {
                likeList.remove(userInfo);
                Post post = postDAO.findById(postId).get();
                post.setLikesUserList(likeList);
                postDAO.save(post);
                return "User "+String.valueOf(userId.intValue())+" like is remove from post "+String.valueOf(postId.intValue());
            }
        }

        likeList.add(new UserInfo(userId, userDAO.findById(userId).get().getUsername()));

        Post post = postDAO.findById(postId).get();
        post.setLikesUserList(likeList);
        postDAO.save(post);
        return "User "+String.valueOf(userId.intValue())+" like post "+String.valueOf(postId.intValue());
    }


    public String disLikePost(Integer userId, Integer postId) {
        if (!postDAO.existsById(postId)) {
            throw new BadRequest("Post doesn't exist");
        }
        if (!userDAO.existsById(userId)) {
            throw new BadRequest("User doesn't exist");
        }
        List<UserInfo> dislikeList = postDAO.findById(postId).get().getDislikesUserList();

        for (UserInfo userInfo : dislikeList) {
            if (userInfo.getUserId().intValue() == userId.intValue()) {
//                List<UserInfo> likedUser=postDAO.findById(postId).get().get
//                if(UserInfo userInfo1 : ){
//                    userInfo1
//                }
                dislikeList.remove(userInfo);
                Post post = postDAO.findById(postId).get();
                post.setDislikesUserList(dislikeList);
                postDAO.save(post);
                return "User "+String.valueOf(userId.intValue())+" dislike is remove from post "+String.valueOf(postId.intValue());
            }
        }

        dislikeList.add(new UserInfo(userId, userDAO.findById(userId).get().getUsername()));

        Post post = postDAO.findById(postId).get();
        post.setDislikesUserList(dislikeList);
        postDAO.save(post);
        return "User "+String.valueOf(userId.intValue())+" dislike post "+String.valueOf(postId.intValue());
    }

    public void postComment(PostComments postComments) {
        if(!postDAO.existsById(postComments.getPostId())){
            throw new BadRequest("Post doesn't exist");
        }
        if(postCommentsDAO.existsById(postComments.getPostCommentId())){
            throw new BadRequest("Comments already exist");
        }
        postComments.setTimeStamp(timeStamp.calculateDateTime());
        Post post=postDAO.findById(postComments.getPostId()).get();
        List<PostComments> postCommentsList=post.getPostCommentsList();
        postCommentsList.add(postComments);
        postDAO.save(post);
    }
}
