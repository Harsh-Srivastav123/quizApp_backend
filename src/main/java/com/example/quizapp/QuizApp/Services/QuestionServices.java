package com.example.quizapp.QuizApp.Services;

import com.example.quizapp.QuizApp.dao.QuestionDAO;
import com.example.quizapp.QuizApp.exceptions.BadRequest;
import com.example.quizapp.QuizApp.exceptions.CustomException;
import com.example.quizapp.QuizApp.geminiAi.GeminiAIService;
import com.example.quizapp.QuizApp.model.CategoryData;
import com.example.quizapp.QuizApp.entity.Question;
import com.example.quizapp.QuizApp.model.CustomQuiz;
import com.example.quizapp.QuizApp.model.QuestionDTO;
import com.example.quizapp.QuizApp.model.QuestionList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    GeminiAIService geminiAIService;

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



    public void delete(Integer id) {
        if(!questionDAO.existsById(id)){
            throw new CustomException("Question Not found unable to delete check the id carefully ");
        }
//        try {
//            questionDAO.deleteById(id);
//            return true;
//        } catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
        questionDAO.deleteById(id);
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

    public Integer categorySize(String category,String difficulty) {
        if(category.equals("all") && difficulty!=null){
            return (int) questionDAO.countByDifficulty(difficulty);
        }
        else if(!category.equals("all") && difficulty!=null){
            return (int) questionDAO.countByDifficultyAndCategory(category,difficulty);
        }
        else if(category.equals("all")){
            return (int)questionDAO.count();
        }

        return questionDAO.categorySize(category);
    }

    public List<QuestionDTO> question(Integer pageNo, Integer pageSize, String difficulty,String category, Integer id)  {
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
        if(difficulty!=null){
            return questionDAO.findByCategoryAndDifficulty(category,difficulty,pg).getContent().stream().map(object->modelMapper.map(object,QuestionDTO.class)).collect(Collectors.toList());
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
                throw new CustomException("category doesn't exist , for information of all category write all");
            }
            categoryDataList.add(getCategoryData(category));
        }

        return categoryDataList;

    }
    public CategoryData getCategoryData(String category){
        CategoryData categoryData=new CategoryData();
        categoryData.setCategory(category);
        categoryData.setEasyQuestion(questionDAO.countByDifficultyAndCategory(category,"easy"));
        categoryData.setHardQuestion(questionDAO.countByDifficultyAndCategory(category,"hard"));
        categoryData.setMediumQuestion(questionDAO.countByDifficultyAndCategory(category,"medium"));
        categoryData.setTotalQuestion(questionDAO.categorySize(category));
        return categoryData;
    }

    public QuestionDTO update(QuestionDTO questionDTO) {
        if(questionDAO.findById(questionDTO.getId()).isPresent()){
            return modelMapper.map(questionDAO.save(modelMapper.map(questionDTO,Question.class)),QuestionDTO.class);
        }
        throw new CustomException("Unable to update question not found with corresponding Id !!");
    }

    public QuestionList customQuiz(List<CustomQuiz> customQuizList) {
        List<QuestionDTO> questionList=new ArrayList<>();
        for (CustomQuiz customQuiz:customQuizList){
            if(customQuiz.getEasy()>0){
                for (Question question:questionDAO.findCustomQuizQuestion(customQuiz.getCategory(),"easy",customQuiz.getEasy())){
                    questionList.add(modelMapper.map(question,QuestionDTO.class));
                }
            }
            if(customQuiz.getMedium()>0){
                for (Question question:questionDAO.findCustomQuizQuestion(customQuiz.getCategory(),"medium",customQuiz.getMedium())){
                    questionList.add(modelMapper.map(question,QuestionDTO.class));
                }
            }
            if(customQuiz.getHard()>0){
                for (Question question:questionDAO.findCustomQuizQuestion(customQuiz.getCategory(),"hard",customQuiz.getHard())){
                    questionList.add(modelMapper.map(question,QuestionDTO.class));
                }
            }
        }
        QuestionList ql=new QuestionList();
        ql.setQuestionList(questionList);
        ql.setTotalQuestion(questionList.size());
        ql.setCategory("Custom Quiz");
        return ql;
    }

    public String aiResponse(Integer id) {
        if(questionDAO.findById(id).isEmpty()){
            throw new BadRequest("unable to find question ");
        }

        return geminiAIService.getGenerativeAIResponse(questionDAO.findById(id).get().getQuestion());
    }
}
