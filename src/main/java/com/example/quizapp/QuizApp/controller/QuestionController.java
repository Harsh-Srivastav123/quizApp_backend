package com.example.quizapp.QuizApp.controller;

import com.example.quizapp.QuizApp.Services.QuestionServices;
import com.example.quizapp.QuizApp.entity.Question;
import com.example.quizapp.QuizApp.model.CategoryData;

import com.example.quizapp.QuizApp.model.CustomQuiz;
import com.example.quizapp.QuizApp.model.QuestionDTO;
import com.example.quizapp.QuizApp.model.QuestionList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/question")

@Slf4j
public class QuestionController {
    @Autowired
    QuestionServices questionServices;


    public record Message(String questionId,String message){}
    @PostMapping("/add")
    public ResponseEntity<Message> addQuestion(@RequestBody QuestionDTO question){
        int id=questionServices.addQuestion(question).getId();
        return new ResponseEntity<>(new Message(Integer.toString(id),"question Added successfully"), HttpStatus.OK);
    }

    @PostMapping("/addList")
    public ResponseEntity<String> addQuestionList(@RequestBody List<QuestionDTO> questionList){
        boolean status= questionServices.addQuestionList(questionList);
        if(status){
            return new ResponseEntity<>("Question List added successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Unable to add questionList",HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping("/showAll")
    public List<Question> getAll(){
        log.info("get all question")
        return questionServices.getAll();
    }


    @GetMapping("")
    public QuestionList getQuestion(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
            @RequestParam(value = "category",defaultValue = "all",required = false) String category,
            @RequestParam(value = "difficulty",required = false) String difficulty,
            @RequestParam(value = "id",defaultValue = "0",required = false) Integer id
    ) {

        QuestionList ql = new QuestionList();
        ql.setQuestionList(questionServices.question(pageNo, pageSize, difficulty,category, id));
        ql.setTotalQuestion(questionServices.categorySize(category,difficulty));
        if(difficulty==null){
            ql.setCategory(category);
        }
        else {
            ql.setCategory(category+" "+difficulty);
        }
        if (!id.equals(0)) {
            ql.setCategory("questionById :- " + id);
            ql.setTotalQuestion(1);
            return ql;
        }
        return ql;
    }

    @PostMapping("/customQuiz")
    public ResponseEntity<QuestionList> customQuiz(@RequestBody List<CustomQuiz> customQuizList){
        return new ResponseEntity<>(questionServices.customQuiz(customQuizList),HttpStatus.OK);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<CategoryData>> getCategoryData(@PathVariable("category") String category){
        List<CategoryData> categoryDataList=questionServices.getCategoryListData(category);

        if(categoryDataList==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(categoryDataList,HttpStatus.OK);
    }
    @PatchMapping("/update")
    public ResponseEntity<Message> update(@RequestBody QuestionDTO question){
        questionServices.update(question);
        return new ResponseEntity<>(new Message(question.getId().toString(),"question update successfully"),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteQuestion(@PathVariable String id){
        questionServices.delete(Integer.parseInt(id));
        return new ResponseEntity<>(new Message(id,"Question deleted successfully"),HttpStatus.OK);
    }


    @GetMapping("/aiResponse/{id}")
    public ResponseEntity<String> aiResponse(@PathVariable Integer id){
        String str= questionServices.aiResponse(id);
        if(str!=null){
            return new ResponseEntity<>(str,HttpStatus.OK);
        }
        return new ResponseEntity<>("Unable to fetch",HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @GetMapping("/test")
//    public boolean test(){
//        return questionServices.testing();
//    }

    @GetMapping("/test1")
    public ResponseEntity<String> test1(){
        log.info("request reach to server");
        return new ResponseEntity<>("Server test1", HttpStatus.OK);
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test2(){
        log.info("request reach to server");
        return new ResponseEntity<>("Server test2", HttpStatus.OK);
    }

}
