package com.example.quizapp.QuizApp.geminiAi;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GenerativeAI {
    String projectId="fast-web-414119";
    String location="us-central1";
    String modelName="gemini-pro";

    @Bean
    public GenerativeModel generativeModel(){
        VertexAI vertexAI = null;
        try {
            vertexAI = new VertexAI(projectId, location);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GenerativeModel model = new GenerativeModel(modelName, vertexAI);
        return model;
    }
}