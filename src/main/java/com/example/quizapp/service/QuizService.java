package com.example.quizapp.service;


import com.example.quizapp.dao.QuestionDao;
import com.example.quizapp.dao.QuizDao;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    QuizDao quizDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category,numQ);

        // creating the quiz
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        try{
            quizDao.save(quiz);
            return new ResponseEntity<String>("success",HttpStatus.CREATED);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<String>("failure",HttpStatus.BAD_REQUEST);

    }


    // To be fixed  ! , Returns only quiz !
    public ResponseEntity<List<Question>> getQuiz(Integer id) {

        try {
//            List<Question> quizQuestions = questionDao.getQuestionsById(id);
            Optional<Quiz> quizQuestions = quizDao.findById(id);
            List<Question> listQuestions = quizQuestions.get().getQuestions();

            return new ResponseEntity<>(listQuestions,HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<List<Question>>(new ArrayList<>(),HttpStatus.OK);
        }
    }



    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);

        List<Question> questionsfromDB = quiz.get().getQuestions();

        // comvert each qn to qn wrapper
        List<QuestionWrapper> questionsForUser = new ArrayList<>();

        for(Question q : questionsfromDB){
            QuestionWrapper qw = new QuestionWrapper(
                    q.getId(),
                    q.getQuestionTitle(),
                    q.getOption1(),
                    q.getOption2(),
                    q.getOption3(),
                    q.getOption4()
            );
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        Quiz quiz = quizDao.findById(id).get();
        // quizDao.findById(id): This searches for a Quiz in the database by its id. It returns an Optional<Quiz>.
        //.get(): This extracts the actual Quiz object from the Optional.

        List<Question> questions = quiz.getQuestions();

        int right =  0;
        int i = 0;

        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;

            i++;
        }

        return new ResponseEntity<>(right,HttpStatus.OK);

    }
}
