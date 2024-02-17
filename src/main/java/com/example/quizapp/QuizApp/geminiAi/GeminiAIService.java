package com.example.quizapp.QuizApp.geminiAi;

import com.google.cloud.vertexai.api.Candidate;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class GeminiAIService {

    @Autowired
    GenerativeModel generativeModel;

    public String getGenerativeAIResponse(String prompt){
        prompt+=" generate hint for the question";
        StringBuilder response=new StringBuilder();
        try {

            for(Candidate candidate:generativeModel.generateContent(prompt).getCandidatesList()){
                System.out.println(candidate.getContent());
                response.append(candidate.getContent());
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.toString();
    }
}
