package com.example.quizapp.QuizApp.controller;

import com.example.quizapp.QuizApp.Services.CloudinaryService;
import com.example.quizapp.QuizApp.Services.QuizService;
import com.example.quizapp.QuizApp.model.*;
import com.example.quizapp.QuizApp.security.JwtHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@RestController
@RequestMapping("/quiz")
public class UserController {
    @Autowired
    QuizService quizService;


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;

    @Autowired
    CloudinaryService cloudinaryService;
    @PostMapping("/response")
    public Result evaluateQuiz(@RequestBody QuizResponse quizResponse){
        if(quizResponse==null){
            return null;
        }
        return quizService.evaluateQuiz(quizResponse);
    }
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id){
        return quizService.getUser(id);
    }
    @GetMapping("/user")
    public List<User> getALlUser(){
        List<com.example.quizapp.QuizApp.model.User> userList =quizService.getAllUser();
        Collections.sort(userList);
        return userList;
    }

    //create user
    @PostMapping(path="/createUser", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })

    public ResponseEntity<User>  createUser(
            @RequestParam("user") String user,
            @RequestPart("image") MultipartFile file){
        System.out.println("reach here");
        Map data= new HashMap();
        User user1;
        // String to User
        ObjectMapper mapper=new ObjectMapper();
        try {
            user1 = mapper.readValue(user, User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if(file!=null){
            data=cloudinaryService.upload(file);
            System.out.println(data);
        }
//        if(user==null){
//            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
//        }
//        else {
//            user1.setProfileUrl(data.get("url").toString());
//        }
        if(quizService.getUserByUserName(user1.getUsername())==null){
            user1.setPassword(passwordEncoder.encode(user1.getPassword()));
            user1.setProfileUrl(data.get("url").toString());
            return new ResponseEntity<>(quizService.createUser(user1),HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequests requests) {
        doAuthentication(requests.getUserName(), requests.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(requests.getUserName());
        String token = this.helper.generateToken(userDetails);
        System.out.println(token);
        JwtResponse response = JwtResponse.builder().token(token)
                .userName(userDetails.getUsername()).build();
        response.setUserId(quizService.getUserByUserName(requests.getUserName()).getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthentication(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }
    @GetMapping("/test")
    public String test(){
        return "Working";
    }


}
