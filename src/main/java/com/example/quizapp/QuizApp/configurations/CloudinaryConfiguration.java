package com.example.quizapp.QuizApp.configurations;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class CloudinaryConfiguration {
    @Bean
    public Cloudinary getCloudinary(){
        Map config=new HashMap();
        config.put("cloud_name","dotyl6ik8");
        config.put("api_key","875887353797574");
        config.put("api_secret","5waXorgjXw_ehV9SvbCWk12mWyk");
        config.put("sceure",true);
        return new Cloudinary(config);
    }
}
