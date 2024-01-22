package com.example.quizapp.QuizApp.Services;

import ch.qos.logback.core.model.INamedModel;
import com.example.quizapp.QuizApp.dao.QuestionDAO;
import com.example.quizapp.QuizApp.dao.UserDAO;
import com.example.quizapp.QuizApp.dao.VerificationDAO;
import com.example.quizapp.QuizApp.model.*;
import com.example.quizapp.QuizApp.utils.CalculateDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Component
public class UserService {



    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    VerificationDAO verificationDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    CalculateDateTime calculateDateTime;

    User user;
    public Result evaluateQuiz(QuizResponse quizResponse) {
        Result result=new Result();

        List<Response> responseList=quizResponse.getResponseList();
        int totalQuestion=quizResponse.getTotalQuestion();
        int nonAttemptQuestion=totalQuestion-responseList.size();
        int rightAnswerUser=0;
        int totalMarks=0;
        int wrongAnswer=0;
        try{

            for (Response response:responseList){
                if(questionDAO.existsById(response.getId())){
                    int id=response.getId();
                    Question questionResponse=questionDAO.getReferenceById(id);
//                  System.out.println(response.getRightAnswer());
//                  System.out.println(questionResponse.getRightAnswer());
                    if(questionResponse.getRightAnswer().equals(response.getRightAnswer())){
                        System.out.println("rightAnswer");
                        rightAnswerUser++;
                        totalMarks+=Integer.parseInt(questionResponse.getMarks());
                    }
                    else {
                        wrongAnswer++;
                    }
                }
            }
            result.setCategory(quizResponse.getCategory());
            result.setTotalQuestion(totalQuestion);
            result.setNonAttemptQuestion(nonAttemptQuestion);
            result.setRightAnswer(rightAnswerUser);
            result.setTotalMarks(totalMarks);
            result.setTotalAttemptQuestion(responseList.size());
            result.setWrongAnswer(wrongAnswer);
            result.setTimeStamp(calculateDateTime.calculateDateTime());
//            if(quizResponse.getUserId()==null){
//                user=new User();
//                user.setTotalMarks(rightAnswerUser);
//                user.setTotalQuiz(1);
//                user.setUserRank(1);
//                List<Result> list=new ArrayList<>();
//                list.add(result);
//                user.setResultList(list);
//            }
//            else {
//
////                System.out.println(user.getResultList());
//            }

            // for giving quiz user must have to create account.


            if(userDAO.existsById(quizResponse.getUserId())){
                user=userDAO.getReferenceById(quizResponse.getUserId());
                user.setTotalMarks(user.getTotalMarks()+totalMarks);
                if(user.getTotalQuiz()!=null){
                    user.setTotalQuiz(1+user.getTotalQuiz());
                }
                else {
                    user.setTotalQuiz(1);
                }

                List<Result> list=user.getResultList();
                list.add(result);
                user.setResultList(list);
            }


            result.setUser(user);
            User curr=userDAO.save(user);
            if(curr!=null){
                evaluateRank();
            }
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private void evaluateRank() {
        List<User> userList=userDAO.findAll();
        Collections.sort(userList);
        int rank=1;
        for (User user:userList){
            user.setUserRank(rank);
            rank++;
            userDAO.save(user);
        }

    }

    public User getUser(Integer id) {
        if(userDAO.existsById(id)){
            return userDAO.findById(id).get();
        }
        return null;
    }

    public List<User> getAllUser() {
        return userDAO.findAll();
    }

    public User createUser(User user) {
        return userDAO.save(user);
    }
    public User getUserByUserName(String userName){
        return userDAO.findByUserName(userName);
    }

    public void saveVerificationToken(User user, String token) {
        verificationDAO.save(new VerificationToken(token,user));
    }

    public List<Integer> validateToken(String token) {
        List<Integer> status=new ArrayList<>();
        VerificationToken verificationToken=verificationDAO.findByToken(token);
        if(verificationToken==null){
            status.add(0);
            return status;
        }
        status.add(verificationToken.getUser().getId());

        User user=verificationToken.getUser();
        Calendar calendar=Calendar.getInstance();
        if((verificationToken.getExpirationTime().getTime()-calendar.getTime().getTime())<=0){
            verificationDAO.delete(verificationToken);
            status.add(1);
            userDAO.delete(verificationToken.getUser());
        }
        user.setEnabled(true);
        userDAO.save(user);
        status.add(2);
        return status;
    }
}
