package com.example.quizapp.QuizApp.controller;

import com.example.quizapp.QuizApp.Services.QuestionServices;
import com.example.quizapp.QuizApp.model.Question;
import com.example.quizapp.QuizApp.model.QuestionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionServices questionServices;
    @PostMapping("/add")
    public boolean addQuestion( @RequestBody Question question){
        return questionServices.addQuestion(question);
    }

    @PostMapping("/addList")
    public boolean addQuestionList(@RequestBody List<Question> questionList){
        return questionServices.addQuestionList(questionList);
    }
    @GetMapping("/show") 
    public QuestionList showQuestion(){
        QuestionList ql=new QuestionList();
        ql.setQuestionList(questionServices.showAllQuestion());
        ql.setTotalQuestion(questionServices.showAllQuestion().size());
        ql.setCategory("all");

        return ql;
    }
    @GetMapping("/question/{category}")
    public QuestionList guestionByCategory(@PathVariable String category){
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
    public boolean update(@RequestBody Question question){
        if(question!=null){
            if(question.getId()!=0){
                return questionServices.addQuestion(question);
            }else {
                return false;
            }

        }
        return false;
    }
}
