package com.example.quizapp.QuizApp.Services;

import com.example.quizapp.QuizApp.dao.SessionDAO;
import com.example.quizapp.QuizApp.dao.SessionUserDAO;
import com.example.quizapp.QuizApp.dao.UserDAO;
import com.example.quizapp.QuizApp.model.Session;
import com.example.quizapp.QuizApp.model.SessionUser;
import com.example.quizapp.QuizApp.model.User;
import com.example.quizapp.QuizApp.utils.CalculateDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class SessionService {

    @Autowired
    SessionDAO sessionDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    SessionUserDAO sessionUserDAO;

    @Autowired
    CalculateDateTime currentTimeStamp;

    @Autowired
    MailSenderService mailSenderService;

    public Session createSession(Session session) {
        session.setDateAndTime(currentTimeStamp.calculateDateTime());
        session.setStartTimeStamp(calculateExpirationTime(session.getDelayDuration()));
        session.setExpiryTimeStamp(calculateExpirationTime(session.getDuration() + session.getDelayDuration()));

        Session currSession = sessionDAO.save(session);
        User user=userDAO.findById(session.getUserId()).get();
        user.getSessionList().add(currSession);
        userDAO.save(user);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (SessionUser sessionUser : currSession.getSessionUserList()) {
                    Optional<User> user;
                    try {
                        user = userDAO.findById(sessionUser.getUserId());
                        if (user.isEmpty()) {
                            log.info("user not found");
                        }
                        sessionUser.setUserName(user.get().getUsername());
                        mailSenderService.sendMailToSessionUser(user.get().getEmail(), currSession.getSessionId(), sessionUser.getSessionUserId(), sessionUser.getUserName());
                        sessionUserDAO.save(sessionUser);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        return currSession;
    }


    private Date calculateExpirationTime(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }

    public List<Session> getSessionDetails(String sessionId) {
        log.info(sessionId);
        if(sessionId.equals("all")){
            return sessionDAO.findAll();
        }
        return Collections.singletonList(sessionDAO.findById(Integer.parseInt(sessionId)).get());
    }
}
