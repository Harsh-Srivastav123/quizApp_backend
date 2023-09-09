package com.example.quizapp.QuizApp.Services;

import com.example.quizapp.QuizApp.dao.QuestionDAO;
import com.example.quizapp.QuizApp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class QuestionServices {
    @Autowired
    QuestionDAO questionDAO;
    public boolean addQuestion(Question question) {
        try{
            questionDAO.save(question);
           return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean addQuestionList(List<Question> questionList){
        try {
            for(Question question:questionList){
                questionDAO.save(question);
            }
           return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Question> showAllQuestion() {
        return questionDAO.findAll();
    }

    public Question getQuestionById(Integer id) {
        return questionDAO.findById(id).get();
    }

    public boolean delete(Integer id) {
        try {
            questionDAO.delete(questionDAO.findById(id).get());
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Question> questionByCategory(String category) {
        return questionDAO.findByCategory(category);
    }
}
