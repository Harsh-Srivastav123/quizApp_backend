package com.example.quizapp.QuizApp.Services;

import com.example.quizapp.QuizApp.dao.QuestionDAO;
import com.example.quizapp.QuizApp.dao.SessionDAO;
import com.example.quizapp.QuizApp.dao.SessionUserDAO;
import com.example.quizapp.QuizApp.dao.UserDAO;
import com.example.quizapp.QuizApp.entity.Question;
import com.example.quizapp.QuizApp.entity.Session;
import com.example.quizapp.QuizApp.entity.SessionUser;
import com.example.quizapp.QuizApp.entity.User;
import com.example.quizapp.QuizApp.exceptions.BadRequest;
import com.example.quizapp.QuizApp.exceptions.CustomException;
import com.example.quizapp.QuizApp.model.*;
import com.example.quizapp.QuizApp.utils.CalculateDateTime;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    QuestionDAO questionDAO;


    @Autowired
    ModelMapper modelMapper;

    public Object verifySession(SessionDTO sessionDTO) {

        SessionVerifyDTO sessionVerifyDTO=modelMapper.map(sessionDTO,SessionVerifyDTO.class);
        HashMap<Integer,String> sessionUserMap=new HashMap<>();
        List<QuestionDTO> questionList=new ArrayList<>();

        for(QuestionDTO question:sessionDTO.getSessionQuestionList()){
            if(question.getId()!=null){
                if(!questionDAO.existsById(question.getId())){
                    throw new BadRequest("question with questionId - "+question.getId().toString()+"  doesn't exist");
                }
                questionList.add(modelMapper.map(questionDAO.findById(question.getId()).get(),QuestionDTO.class));
            }
            else {
                questionList.add(question);
            }
        }
        sessionVerifyDTO.setSessionVerifyQuestionList(questionList);

        for(SessionUser sessionUser:sessionDTO.getSessionUserList()){
            if(!userDAO.existsById(sessionUser.getUserId())){
                throw new BadRequest("user with userId - "+sessionUser.getUserId().toString()+"  doesn't exist");
            }
            sessionUserMap.put(sessionUser.getUserId(),userDAO.findById(sessionUser.getUserId()).get().getUsername());
        }
        sessionVerifyDTO.setUserMapList(sessionUserMap);
        return sessionVerifyDTO;
    }

    public record sessionMessage(String message, Long timeLeft){}

    public SessionDTO createSession(SessionDTO sessionDTO) {
        List<QuestionDTO> updatedQuestionList=new ArrayList<>();
        for (QuestionDTO questionDTO : sessionDTO.getSessionQuestionList()) {
            if (questionDTO.getId() == null) {

                int rightOption=-1;
                if(questionDTO.getRightAnswer().equals(questionDTO.getOptions1())) rightOption=1;
                else if(questionDTO.getRightAnswer().equals(questionDTO.getOptions2())) rightOption=2;
                else if(questionDTO.getRightAnswer().equals(questionDTO.getOptions3())) rightOption=3;
                else if(questionDTO.getRightAnswer().equals(questionDTO.getOptions4())) rightOption=4;
                questionDTO.setRightOption(rightOption);
                Question question=questionDAO.save(modelMapper.map(questionDTO, Question.class));
                updatedQuestionList.add(new QuestionDTO(question.getId()));
                log.info("new question created in session",question);
            }
            else {
                updatedQuestionList.add(new QuestionDTO(questionDTO.getId()));
            }
        }
        sessionDTO.setSessionQuestionList(updatedQuestionList);
        if (sessionDTO.getSessionId() != null) {

            throw new CustomException("session details is missing");
            //throw exception
        }
        for(SessionUser user:sessionDTO.getSessionUserList()){
            if(!userDAO.existsById(user.getUserId())){
               throw new CustomException("User not found check the user id carefully!!") ;
            }
        }
        Session session = modelMapper.map(sessionDTO, Session.class);
        session.setDateAndTime(currentTimeStamp.calculateDateTime());
        session.setStartTimeStamp(calculateExpirationTime(session.getDelayDuration()));
        session.setExpiryTimeStamp(calculateExpirationTime(session.getDuration() + session.getDelayDuration()));

        Session currSession = sessionDAO.save(session);
        User user = userDAO.findById(session.getUserId()).get();
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

        return modelMapper.map(currSession,SessionDTO.class);
    }


    private Date calculateExpirationTime(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }

    public Object getSessionDetails(Integer sessionId) {
        log.info(sessionId.toString());
//        log.info(sessionId);
//        if (sessionId.equals("all")) {
//            //sessionDAO.findAll();
//            return sessionDAO.findAll().stream().map(object -> modelMapper.map(object, SessionDTO.class)).collect(Collectors.toList());
//        }
        //Collections.singletonList(sessionDAO.findById(Integer.parseInt(sessionId)).get());
        if (!sessionDAO.existsById(sessionId)) {
            //throw exception
            throw new CustomException("Session id not exist check the carefully");
        }

        Session session=sessionDAO.findById(sessionId).get();

        Date currTimeStamp=new Date();
        if(currTimeStamp.before(session.getStartTimeStamp())){
//            log.info("reach here");
           // Duration duration=Duration.between((Temporal) session.getStartTimeStamp(), (Temporal) currTimeStamp);
            LocalDateTime dateTime1 = session.getStartTimeStamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime dateTime2 = currTimeStamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return new sessionMessage("Session has not started yet !!",Duration.between(dateTime2,dateTime1).toMinutes() );
        }

        //rank changes handle here
//        return Collections.singletonList(modelMapper.map(sessionDAO.findById(Integer.parseInt(sessionId)).get(), SessionDTO.class));


        List<SessionUser> sessionUserListAttempted=new ArrayList<>();
        List<SessionUser> sessionUserListNonAttempted=new ArrayList<>();
        for(SessionUser sessionUser:session.getSessionUserList()){
            if(sessionUser.isCompleted()){
                sessionUserListAttempted.add(sessionUser);
            }
            else {
                sessionUserListNonAttempted.add(sessionUser);
            }
        }
        sessionUserListNonAttempted.addAll(sessionUserListAttempted);
        session.setSessionUserList(sessionUserListNonAttempted);
        return modelMapper.map(session,SessionDTO.class);
    }

    public SessionResult submitResponse(SessionResponse sessionResponse) {



        boolean status=false;

        SessionResult sessionResult=new SessionResult();
        if(sessionDAO.findById(sessionResponse.getSessionId()).isEmpty()){
            //throw exception
            throw  new CustomException("Unable find Session check the credential carefully");
        }


        //check expiration time of response
        Session session=sessionDAO.findById(sessionResponse.getSessionId()).get();
        Date currTimeStamp=new Date();

        if(currTimeStamp.before(session.getStartTimeStamp())){
            throw new BadRequest("Session Quiz hasn't started yet !!");
        }
        if(currTimeStamp.after(session.getExpiryTimeStamp())){
            throw new BadRequest("Response can't be submitted Timeout!!!");
        }


        if(userDAO.findById(sessionResponse.getUserId()).isEmpty()){
            //throw exception
            throw  new BadRequest("Unable find User check the credential carefully");
        }


        for(SessionUser sessionUser:session.getSessionUserList()){
            log.info("sessionUser  "+sessionUser.getUserId().toString());
            if(sessionUser.getSessionUserId().equals(sessionResponse.getSessionUserId()) && sessionResponse.getUserId().intValue()==sessionUser.getUserId().intValue() && !sessionUser.isCompleted()){
                // user found now calculate response
                log.info("user found");
                status=true;
                int rightAnswer=0;
                int wrongAnswer=0;
                int marks=0;
                for(Response response:sessionResponse.getResponseList()){
                    if(questionDAO.findById(response.getId()).isEmpty()){
                        throw new BadRequest("Question not found check the question Id carefully");
                    }
                    //questionDAO.getReferenceById(response.getId()).getRightAnswer().equals(response.getRightAnswer())
                    if(questionDAO.findById(response.getId()).get().getRightOption().intValue()== response.getRightOption().intValue()){
                        rightAnswer++;
                        marks+=questionDAO.getReferenceById(response.getId()).getMarks();
                    }
                    else {
                        wrongAnswer++;
                    }
                }
                // created result
                sessionResult.setCorrect(rightAnswer);
                sessionResult.setWrong(wrongAnswer);
                sessionResult.setTotalQuestion(session.getSessionQuestionList().size());
                sessionResult.setMessage("Result Calculated Successfully");
                sessionResult.setMarks(marks);
                sessionResult.setSessionUserId(sessionUser.getSessionUserId());
                sessionResult.setUserId(sessionUser.getUserId());

                // sessionUser result
                sessionUser.setMarks(marks);
                sessionUser.setCompleted(true);
                sessionUser.setUserName(userDAO.findById(sessionResponse.getUserId()).get().getUsername());
                sessionUser.setSessionUserId(sessionUser.getSessionUserId());
                sessionUser.setSubmissionTimeStamp(currentTimeStamp.calculateDateTime());
                sessionUserDAO.save(sessionUser);
            }
        }
        if(!status){
            throw new CustomException("Unable to calculate session response check the credentials carefully");
        }
        calculateSessionUserRank(session.getSessionUserList(),session);
        return sessionResult;
    }

    private void calculateSessionUserRank(List<SessionUser> sessionUserList,Session session) {
        Collections.sort(sessionUserList);
        int rank=1;
        for (SessionUser sessionUser:sessionUserList){
            if(sessionUser.isCompleted()){
                sessionUser.setSessionRank(rank++);
            }
        }
        session.setSessionUserList(sessionUserList);
        sessionDAO.save(session);
    }

    public void deleteSession(int sessionId) {
        if(!sessionDAO.existsById(sessionId)){
            throw new CustomException("Session Not Found check the sessionId carefully");
        }
        sessionDAO.deleteById(sessionId);
    }
}



