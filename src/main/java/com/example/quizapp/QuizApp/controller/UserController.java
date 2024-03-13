package com.example.quizapp.QuizApp.controller;

import com.example.quizapp.QuizApp.Services.CloudinaryService;
import com.example.quizapp.QuizApp.Services.UserService;
import com.example.quizapp.QuizApp.entity.Result;

import com.example.quizapp.QuizApp.events.RegistrationCompleteEvent;
import com.example.quizapp.QuizApp.model.*;
import com.example.quizapp.QuizApp.security.JwtAuthenticationFilter;
import com.example.quizapp.QuizApp.security.JwtHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
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

@CrossOrigin()
@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    UserService userService;


    @Autowired
    ApplicationEventPublisher publisher;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JwtHelper helper;
    public record Message(String userId,String message){}
    @Autowired
    CloudinaryService cloudinaryService;
    @PostMapping("/response")
    public ResponseEntity<DetailResult> evaluateQuiz(@RequestBody QuizResponse quizResponse){

        quizResponse.setUserId(userService.getUserByUserName(jwtAuthenticationFilter.getUserNameByToken()).getId());
        return new ResponseEntity<>(userService.evaluateQuiz(quizResponse),HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public UserDTO getUser(@PathVariable Integer id){
        return userService.getUser(id);
    }
    @GetMapping("/user")
    public List<UserDTO> getALlUser(){
        List<UserDTO> userList = userService.getAllUser();
        Collections.sort(userList);
        return userList;
    }

    //create user
    @PostMapping(path="/createUser", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })

    public ResponseEntity<Message>  createUser(
            @RequestParam("user") String user,
            @RequestPart(value = "image",required = false) MultipartFile file, final HttpServletRequest request){
//        System.out.println("reach here");
        Map data= new HashMap();
        UserDTO user1;
        // String to User
        ObjectMapper mapper=new ObjectMapper();
        try {
            user1 = mapper.readValue(user, UserDTO.class);
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

        if(!userService.existByUserName(user1.getUserName())  && !userService.existById(user1.getId())){
            user1.setPassword(passwordEncoder.encode(user1.getPassword()));
            if(data.get("url")!=null){
                user1.setProfileUrl(data.get("url").toString());
            }
            UserDTO newUser=userService.createUser(user1);
            publisher.publishEvent(new RegistrationCompleteEvent(
                            newUser,
                            applicationUrl(request)
                    )
            );
            return new ResponseEntity<>(new Message(newUser.getId().toString(),"User Created Successfully verify the email to further procced"),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message(null, "Unable to create user , user is already exists"), HttpStatus.BAD_REQUEST);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

    @GetMapping("verifyRegistration")
    public ResponseEntity<Message> verifyRegistration(@RequestParam("token") String token){
        List<Integer> status=userService.validateToken(token);
        if(status.get(0)==0){
            return new ResponseEntity<>(new Message(null,"enter valid token"),HttpStatus.BAD_REQUEST);
        }
        if(status.get(1)==1){
            return new ResponseEntity<>(new Message(status.get(0).toString(),"token expired"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message(status.get(0).toString(),"token  verified Successfully "),HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequests requests) {
        doAuthentication(requests.getUserName(), requests.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(requests.getUserName());
        String token = this.helper.generateToken(userDetails);
        System.out.println(token);
        JwtResponse response = JwtResponse.builder().token(token)
                .userName(userDetails.getUsername()).build();
        response.setUserId(userService.getUserByUserName(requests.getUserName()).getId());
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

    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<Message> deleteUser(@PathVariable String id){
        userService.deleteUser(Integer.parseInt(id));
        return new ResponseEntity<>(new Message(id,"User deleted successfully"),HttpStatus.OK);
    }
    @GetMapping("/test")
    public String test(){
        return "Working";
    }


}
