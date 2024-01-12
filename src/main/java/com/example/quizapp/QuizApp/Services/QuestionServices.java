package com.example.quizapp.QuizApp.Services;

import com.example.quizapp.QuizApp.dao.QuestionDAO;
import com.example.quizapp.QuizApp.model.Question;
import com.example.quizapp.QuizApp.model.QuestionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Component
public class QuestionServices {
    @Autowired
    QuestionDAO questionDAO;

    public Question addQuestion(Question question) {
        try{
            return questionDAO.save(question);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
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
            questionDAO.deleteById(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

//    public List<Question> questionByCategory(String category) {
//        return questionDAO.categorySize(category);
//    }

    public QuestionList generatePaper() {
        List<Question> easyQuestion=questionDAO.findByDifficulty("easy");
        List<Question> mediumQuestion=questionDAO.findByDifficulty("medium");
        List<Question> difficultQuestion=questionDAO.findByDifficulty("hard");
        List<Question> questionPaper=new ArrayList<>();
        questionPaper.add(easyQuestion.get(addQuestion(easyQuestion)));
        questionPaper.add(easyQuestion.get(addQuestion(easyQuestion)));
        questionPaper.add(easyQuestion.get(addQuestion(easyQuestion)));
        questionPaper.add(easyQuestion.get(addQuestion(easyQuestion)));

        questionPaper.add(mediumQuestion.get(addQuestion(mediumQuestion)));
        questionPaper.add(mediumQuestion.get(addQuestion(mediumQuestion)));
        questionPaper.add(mediumQuestion.get(addQuestion(mediumQuestion)));
        questionPaper.add(mediumQuestion.get(addQuestion(mediumQuestion)));
        questionPaper.add(mediumQuestion.get(addQuestion(mediumQuestion)));

        questionPaper.add(difficultQuestion.get(addQuestion(difficultQuestion)));
        questionPaper.add(difficultQuestion.get(addQuestion(difficultQuestion)));
        questionPaper.add(difficultQuestion.get(addQuestion(difficultQuestion)));
        return new QuestionList(questionPaper,12,"QuestionPaper");
    }
    public int addQuestion(List<Question> questionsList){
        Random random=new Random();
        int i=random.nextInt(questionsList.size()-1);
        questionsList.remove(i);
        return i;
    }

    public Integer categorySize(String category) {
        return questionDAO.categorySize(category);
    }

    public List<Question> question(Integer pageNo, Integer pageSize, String category, Integer id) {
        List<Question> questionList=new ArrayList<>();
        if(id !=0){
            questionList.add(questionDAO.findById(id).get());
            return  questionList;
        }
        Pageable pg= PageRequest.of(pageNo,pageSize);
        if(category.equals("all")){
            Page<Question> page =questionDAO.findAll(pg);
            return page.getContent();
        }
        return questionDAO.findByCategory(category,pg).getContent();
    }
}
