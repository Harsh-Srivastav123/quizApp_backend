package com.example.quizapp.QuizApp.Services;

import com.example.quizapp.QuizApp.dao.QuestionDAO;
import com.example.quizapp.QuizApp.dao.UserDAO;
import com.example.quizapp.QuizApp.dao.VerificationDAO;
import com.example.quizapp.QuizApp.entity.Question;
import com.example.quizapp.QuizApp.entity.Result;
import com.example.quizapp.QuizApp.entity.User;
import com.example.quizapp.QuizApp.entity.VerificationToken;
import com.example.quizapp.QuizApp.exceptions.CustomException;
import com.example.quizapp.QuizApp.model.DetailResult;
import com.example.quizapp.QuizApp.model.QuizResponse;
import com.example.quizapp.QuizApp.model.Response;
import com.example.quizapp.QuizApp.model.UserDTO;
import com.example.quizapp.QuizApp.utils.CalculateDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService {


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    VerificationDAO verificationDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    CalculateDateTime calculateDateTime;

    User user;

    public DetailResult evaluateQuiz(QuizResponse quizResponse) {
        Result result = new Result();
        DetailResult detailResult = new DetailResult();
        List<Response> resultList = new ArrayList<>();

        List<Response> responseList = quizResponse.getResponseList();
        int totalQuestion = quizResponse.getTotalQuestion();
        int nonAttemptQuestion = totalQuestion - responseList.size();
        int rightAnswerUser = 0;
        int totalMarks = 0;
        int wrongAnswer = 0;
        try {
            for (Response response : responseList) {
                Response resultResponse = new Response();
                if (questionDAO.existsById(response.getId())) {
                    int id = response.getId();
                    if(questionDAO.findById(id).isEmpty()){
                        throw new CustomException("question not found check the id carefully");
                    }
                    Question questionResponse = questionDAO.findById(id).get();

                    resultResponse.setQuestion(questionResponse.getQuestion());
                    resultResponse.setId(id);
                    resultResponse.setRightAnswer(questionResponse.getRightAnswer());
                    System.out.println(response.getRightOption());
                    if(response.getRightOption() ==1) resultResponse.setSubmitResponse(questionResponse.getOptions1());
                    else if(response.getRightOption() ==2) resultResponse.setSubmitResponse(questionResponse.getOptions2());
                    else if(response.getRightOption() ==3) resultResponse.setSubmitResponse(questionResponse.getOptions3());
                    else if(response.getRightOption() ==4) resultResponse.setSubmitResponse(questionResponse.getOptions4());
//                    switch (response.getRightOption()){
//                        case 1:resultResponse.setSubmitResponse(questionResponse.getOptions1());
//                        case 2:resultResponse.setSubmitResponse(questionResponse.getOptions2());
//                        case 3:resultResponse.setSubmitResponse(questionResponse.getOptions3());
//                        case 4:resultResponse.setSubmitResponse(questionResponse.getOptions4());
//                    }
                    resultResponse.setMarks(questionResponse.getMarks()) ;
//                  System.out.println(response.getRightAnswer());
//                  System.out.println(questionResponse.getRightAnswer());
                    //questionResponse.getRightAnswer().equals(response.getRightAnswer())
                    if (questionResponse.getRightOption().intValue()==response.getRightOption().intValue()) {
                        resultResponse.setResult(true);
//                        System.out.println("rightAnswer");
                        rightAnswerUser++;
                        totalMarks += questionResponse.getMarks();
                    } else {
                        resultResponse.setResult(false);
                        wrongAnswer++;
                    }
                } else {
                    throw new CustomException("Question not found check the Id carefully");
                }
                resultList.add(resultResponse);
            }
            result.setCategory(quizResponse.getCategory());
            result.setTotalQuestion(totalQuestion);
            result.setNonAttemptQuestion(nonAttemptQuestion);
            result.setRightAnswer(rightAnswerUser);
            result.setTotalMarks(totalMarks);
            result.setTotalAttemptQuestion(responseList.size());
            result.setWrongAnswer(wrongAnswer);
            result.setTimeStamp(calculateDateTime.calculateDateTime());
            result.setMaximumMarks(quizResponse.getMaximumMarks());
            detailResult.setResult(result);
            detailResult.setResponseList(resultList);
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


            if (userDAO.findById(quizResponse.getUserId()).isEmpty()) {
                throw new CustomException("Unable to find User check Id carefully");
            }
            user = userDAO.findById(quizResponse.getUserId()).get();
            user.setTotalMarks(user.getTotalMarks() + totalMarks);
            if (user.getTotalQuiz() != 0) {
                user.setTotalQuiz(1 + user.getTotalQuiz());
            } else {
                user.setTotalQuiz(1);
            }

            List<Result> list = user.getResultList();
            list.add(result);
            user.setResultList(list);


            result.setUser(user);
            userDAO.save(user);
            evaluateRank();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return detailResult;
    }

    private void evaluateRank() {
        List<User> userList = userDAO.findAll();
        Collections.sort(userList);
        int rank = 1;
        for (User user : userList) {
            user.setUserRank(rank);
            rank++;
            userDAO.save(user);
        }

    }

    public UserDTO getUser(Integer id) {

        if (userDAO.findById(id).isEmpty()) {
            throw new CustomException("User not found check the userId carefully");
        }
        return modelMapper.map(userDAO.findById(id).get(), UserDTO.class);
    }

    public List<UserDTO> getAllUser() {
        return userDAO.findAll().stream().map(object -> modelMapper.map(object, UserDTO.class)).collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO user) {
        return modelMapper.map(userDAO.save(modelMapper.map(user, User.class)), UserDTO.class);
    }

    public UserDTO getUserByUserName(String userName) {
        System.out.println("Hello this" + userDAO.findByUserName(userName).getUsername());
        return modelMapper.map(userDAO.findByUserName(userName), UserDTO.class);
    }

    public void saveVerificationToken(UserDTO userDTO, String token) {
        User user = userDAO.findByUserName(userDTO.getUserName());
        verificationDAO.save(new VerificationToken(token, user));
    }

    public List<Integer> validateToken(String token) {
        List<Integer> status = new ArrayList<>();
        VerificationToken verificationToken = verificationDAO.findByToken(token);
        if (verificationToken == null) {
            status.add(0);
            return status;
        }
        status.add(verificationToken.getUser().getId());

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationDAO.delete(verificationToken);
            status.add(1);
            userDAO.delete(verificationToken.getUser());
        }
        user.setEnabled(true);
        userDAO.save(user);
        status.add(2);
        return status;
    }

    public boolean existById(Integer id) {
        if (id == null) {
            return false;
        }
        return userDAO.existsById(id);

    }

    public boolean existByUserName(String userName) {
        return userDAO.findByUserName(userName) != null;
    }

    public void deleteUser(int id) {
        if (!userDAO.existsById(id)) {
            throw new CustomException("Unable to delete user not found !! check the id carefully");
        }
    }
}
