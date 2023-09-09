package com.example.quizapp.QuizApp.Services;

import com.example.quizapp.QuizApp.dao.QuestionDAO;
import com.example.quizapp.QuizApp.dao.UserDAO;
import com.example.quizapp.QuizApp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizService {
    @Autowired
    Result result;

    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    UserDAO userDAO;

    User user;
    public Result evaluateQuiz(QuizResponse quizResponse) {

        List<Response> responseList=quizResponse.getResponseList();
        int totalQuestion=quizResponse.getTotalQuestion();
        int nonAttemptQuestion=totalQuestion-responseList.size();
        int rightAnswerUser=0;
        int wrongAnswer=0;
        try{

            for (Response response:responseList){
                int id=response.getId();
                String rightAnswer=questionDAO.getReferenceById(id).getRightAnswer();
                if(rightAnswer.equals(response.getRightAnswer())){
                    rightAnswerUser++;
                }
                else {
                    wrongAnswer++;
                }
            }
            result.setCategory(quizResponse.getCategory());
            result.setTotalQuestion(totalQuestion);
            result.setNonAttemptQuestion(nonAttemptQuestion);
            result.setRightAnswer(rightAnswerUser);
            result.setTotalAttemptQuestion(responseList.size());
            result.setWrongAnswer(wrongAnswer);
            if(quizResponse.getUserId()==null){
                user=new User();
                user.setTotalMarks(rightAnswerUser);
                user.setTotalQuiz(1);
                user.setUserRank(1);
                List<Result> list=new ArrayList<>();
                list.add(result);
                user.setResultList(list);
            }
            else {
                user=userDAO.getReferenceById(quizResponse.getUserId());
                user.setTotalMarks(user.getTotalMarks()+rightAnswerUser);
                user.setTotalQuiz(1+user.getTotalQuiz());
                user.setUserRank(user.getUserRank());
                List<Result> list=user.getResultList();
                list.add(result);
                user.setResultList(list);
//                System.out.println(user.getResultList());
            }
            result.setUser(user);
            User curr=userDAO.save(user);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public User getUser(Integer id) {
        return userDAO.findById(id).get();
    }
}
