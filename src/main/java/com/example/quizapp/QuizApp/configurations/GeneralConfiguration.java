package com.example.quizapp.QuizApp.configurations;

import com.example.quizapp.QuizApp.filter.GatewayFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;


@Configuration
public class GeneralConfiguration {

    public static final String HANDLER_EXCEPTION_RESOLVER = "handlerExceptionResolver";


    private static final String GATEWAY_FILTER_URL_PATTERN = "/*";
    private static final int GATEWAY_FILTER_ORDER=1;

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public GatewayFilter loadGatewayFilter(@Qualifier(HANDLER_EXCEPTION_RESOLVER) HandlerExceptionResolver resolver) {
        return new GatewayFilter(resolver);


    }

    @Bean
    public FilterRegistrationBean<GatewayFilter> GatewayFilterRegistrationBean(GatewayFilter gatewayFilter) {
        FilterRegistrationBean<GatewayFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(gatewayFilter);
        registrationBean.addUrlPatterns(GATEWAY_FILTER_URL_PATTERN);
        registrationBean.setOrder(GATEWAY_FILTER_ORDER);
        return registrationBean;
    }


     @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.addAllowedHeader("*");
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/", config);
        return new CorsFilter(source);
    }
}
