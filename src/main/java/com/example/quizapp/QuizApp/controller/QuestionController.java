package com.example.quizapp.QuizApp.controller;

import com.example.quizapp.QuizApp.Services.QuestionServices;
import com.example.quizapp.QuizApp.model.Question;
import com.example.quizapp.QuizApp.model.QuestionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionServices questionServices;
    @PostMapping("/add")
    public Question addQuestion( @RequestBody Question question){
        return questionServices.addQuestion(question);
    }

    @PostMapping("/addList")
    public boolean addQuestionList(@RequestBody List<Question> questionList){
        return questionServices.addQuestionList(questionList);
    }
//    @GetMapping("/delete/{id}")
//    public Boolean deleteById(@PathVariable Integer id){
//        return questionServices.delete(id);
//    }
    @GetMapping("/questionPaper")
    public QuestionList generatePaper(){
        return questionServices.generatePaper();
    }
    @GetMapping("/show") 
    public QuestionList showQuestion(){
        QuestionList ql=new QuestionList();
        ql.setQuestionList(questionServices.showAllQuestion());
        ql.setTotalQuestion(questionServices.showAllQuestion().size());
        ql.setCategory("all");

        return ql;
    }
    @GetMapping("/category/{category}")
    public QuestionList questionByCategory(@PathVariable String category){
        QuestionList ql=new QuestionList();
        ql.setQuestionList(questionServices.questionByCategory(category));
        ql.setTotalQuestion(questionServices.questionByCategory(category).size());
        ql.setCategory(category);

        return ql;
    }
    @GetMapping("/questionById/{id}")
    public Question getQuestionById(@PathVariable Integer id){
        return questionServices.getQuestionById(id);
    }
    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable Integer id){
        return questionServices.delete(id);
    }
    @PostMapping("/update")
    public Question update(@RequestBody Question question){
        if(question!=null){
            if(question.getId()!=0){
                return questionServices.addQuestion(question);
            }else {
                return null;
            }

        }
       return null;
    }
    @GetMapping("/difficulty/{difficulty}")
    public QuestionList getQuestionWithDifficulty(@PathVariable  String difficulty){
        List<Question> questionList=questionServices.getQuestionByDifficulty(difficulty);
        QuestionList ql=new QuestionList();
        ql.setCategory(difficulty);
        ql.setQuestionList(questionList);
        ql.setTotalQuestion(questionList.size());
        return ql;
    }
}
