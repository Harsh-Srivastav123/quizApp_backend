package com.example.quizapp.QuizApp.controller;

import com.example.quizapp.QuizApp.Services.QuestionServices;
import com.example.quizapp.QuizApp.exceptions.QuestionNotFound;
import com.example.quizapp.QuizApp.model.CategoryData;
import com.example.quizapp.QuizApp.model.Question;
import com.example.quizapp.QuizApp.model.QuestionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import java.util.ArrayList;
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
//    @GetMapping("/show")
//    public QuestionList showQuestion(){
//        QuestionList ql=new QuestionList();
//        ql.setQuestionList(questionServices.showAllQuestion());
//        ql.setTotalQuestion(questionServices.showAllQuestion().size());
//        ql.setCategory("all");
//
//        return ql;
//    }
//    @GetMapping("/category/{category}")
//    public QuestionList questionByCategory(@PathVariable String category){
//        QuestionList ql=new QuestionList();
//        ql.setQuestionList(questionServices.questionByCategory(category));
//        ql.setTotalQuestion(questionServices.questionByCategory(category).size());
//        ql.setCategory(category);
//
//        return ql;
//    }
//    @GetMapping("/questionById/{id}")
//    public Question getQuestionById(@PathVariable Integer id){
//        return questionServices.getQuestionById(id);
//    }
//    @GetMapping("/delete/{id}")
//    public boolean delete(@PathVariable Integer id){
//        return questionServices.delete(id);
//    }


    @GetMapping("")
    public QuestionList getQuestion(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
            @RequestParam(value = "category",defaultValue = "all",required = false) String category,
            @RequestParam(value = "id",defaultValue = "0",required = false) Integer id
    ) throws QuestionNotFound {

        QuestionList ql = new QuestionList();
        ql.setQuestionList(questionServices.question(pageNo, pageSize, category, id));
        ql.setTotalQuestion(questionServices.categorySize(category));
        ql.setCategory(category);
        if (!id.equals(0)) {
            ql.setCategory("questionById :- " + id);
            ql.setTotalQuestion(1);
            return ql;
        }
        return ql;
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<CategoryData>> getCategoryData(@PathVariable("category") String category){
        List<CategoryData> categoryDataList=questionServices.getCategoryListData(category);

        if(categoryDataList==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(categoryDataList,HttpStatus.OK);
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
}
