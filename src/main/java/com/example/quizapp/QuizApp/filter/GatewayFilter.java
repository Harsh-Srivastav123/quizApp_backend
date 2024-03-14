package com.example.quizapp.QuizApp.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static java.rmi.server.LogStream.log;

@Slf4j
public class GatewayFilter extends OncePerRequestFilter {

    // MEMO ::: this bean is registered by spring
    // WebMvcAutoConfiguration$EnableWebMvcConfiguration.class
    protected HandlerExceptionResolver resolver;


    public GatewayFilter(
            HandlerExceptionResolver resolver) {
        this.resolver = resolver;

    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.info(request.getHeader("Origin"));
            log.info(request.getMethod());
//            android.util.Log.i(TAG, "doFilterInternal: ");(request.getHeader("origin").toString());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            GatewayFilter.log.error("Spring Security Filter Chain Exception:", e);
            resolver.resolveException(request, response, null, e);
        }
    }
}


