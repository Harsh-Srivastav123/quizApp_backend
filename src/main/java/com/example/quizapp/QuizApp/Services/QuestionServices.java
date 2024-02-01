package com.example.quizapp.QuizApp.Services;

import com.example.quizapp.QuizApp.dao.QuestionDAO;
import com.example.quizapp.QuizApp.exceptions.CustomException;
import com.example.quizapp.QuizApp.model.CategoryData;
import com.example.quizapp.QuizApp.entity.Question;
import com.example.quizapp.QuizApp.model.QuestionDTO;
import com.example.quizapp.QuizApp.model.QuestionList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class QuestionServices {
    @Autowired
    QuestionDAO questionDAO;



    @Autowired
    ModelMapper modelMapper;

    public QuestionDTO addQuestion(QuestionDTO question) {
        return modelMapper.map(questionDAO.save(modelMapper.map(question,Question.class)),QuestionDTO.class);
    }
    public boolean addQuestionList(List<QuestionDTO> questionList){
        try {
            for(QuestionDTO questionDTO:questionList){
                //mapper
                Question question=modelMapper.map(questionDTO,Question.class);
                questionDAO.save(question);
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<QuestionDTO> showAllQuestion() {
        return questionDAO.findAll().stream().map(object->modelMapper.map(object,QuestionDTO.class)).collect(Collectors.toList());
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
        List<QuestionDTO> easyQuestion=questionDAO.findByDifficulty("easy").stream().map(object -> modelMapper.map(object, QuestionDTO.class)).collect(Collectors.toList());
        List<QuestionDTO> mediumQuestion=questionDAO.findByDifficulty("medium").stream().map(object -> modelMapper.map(object, QuestionDTO.class)).collect(Collectors.toList());
        List<QuestionDTO> difficultQuestion=questionDAO.findByDifficulty("hard").stream().map(object -> modelMapper.map(object, QuestionDTO.class)).collect(Collectors.toList());
        System.out.println(easyQuestion);
        List<QuestionDTO> questionPaper=new ArrayList<>();
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
        System.out.println(questionPaper);

        return new QuestionList(questionPaper,12,"QuestionPaper");
    }
    public int addQuestion(List<QuestionDTO > questionsList){
        Random random=new Random();
        int i=random.nextInt(questionsList.size()-1);
        questionsList.remove(i);
        return i;
    }

    public Integer categorySize(String category) {
        if(category.equals("all")){
            return (int)questionDAO.count();
        }

        return questionDAO.categorySize(category);
    }

    public List<QuestionDTO> question(Integer pageNo, Integer pageSize, String category, Integer id)  {
        List<QuestionDTO> questionList=new ArrayList<>();
        if(id !=0){
            if(questionDAO.findById(id).isEmpty()){
                throw new CustomException("Question not found with corresponding Id !!");
            }
            questionList.add(modelMapper.map(questionDAO.findById(id).get(),QuestionDTO.class));
            return  questionList;
        }
        Pageable pg= PageRequest.of(pageNo,pageSize);
        if(category.equals("all")){
            Page<Question> page =questionDAO.findAll(pg);
            return page.getContent().stream().map(object->modelMapper.map(object,QuestionDTO.class)).collect(Collectors.toList());
        }
        return questionDAO.findByCategory(category,pg).getContent().stream().map(object->modelMapper.map(object,QuestionDTO.class)).collect(Collectors.toList());
    }

    public List<CategoryData> getCategoryListData(String category) {
        List<CategoryData> categoryDataList=new ArrayList<>();
        if(category.equals("all")){
            List<String> categoryList=questionDAO.allCategory();
            for (String str:categoryList){
                categoryDataList.add(getCategoryData(str));
            }
            return categoryDataList;
        }

        else {
            if(!questionDAO.existsByCategory(category)){
                throw new CustomException("No Question found in this category");
            }
            categoryDataList.add(getCategoryData(category));
        }

        return categoryDataList;

    }
    public CategoryData getCategoryData(String category){
        CategoryData categoryData=new CategoryData();
        categoryData.setCategory(category);
        categoryData.setEasyQuestion(questionDAO.categoryWithDifficulty(category,"easy"));
        categoryData.setHardQuestion(questionDAO.categoryWithDifficulty(category,"hard"));
        categoryData.setMediumQuestion(questionDAO.categoryWithDifficulty(category,"medium"));
        categoryData.setTotalQuestion(questionDAO.categorySize(category));
        return categoryData;
    }

    public QuestionDTO update(QuestionDTO questionDTO) {
        if(questionDAO.findById(questionDTO.getId()).isPresent()){
            return modelMapper.map(questionDAO.save(modelMapper.map(questionDTO,Question.class)),QuestionDTO.class);
        }
        throw new CustomException("Unable to update question not found with corresponding Id !!");
    }
}
