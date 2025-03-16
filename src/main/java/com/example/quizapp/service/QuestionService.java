package com.example.quizapp.service;

import com.example.quizapp.model.Question;
import com.example.quizapp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {

        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }



    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {


        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {

        try{
            questionDao.save(question);
            return new ResponseEntity<String>("success",HttpStatus.CREATED);
            // now instead of just success msg it will send CREATED in reponse !
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<String>("failure",HttpStatus.BAD_REQUEST);
    }

    public  ResponseEntity<String> deleteQuestion(Question question) {

        try{
            questionDao.delete(question);
            return new ResponseEntity<String>("success",HttpStatus.GONE);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<String>("failure",HttpStatus.BAD_REQUEST);
    }


    public  ResponseEntity<String> updateQuestion(Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<String>("success",HttpStatus.OK);
            // now instead of just success msg it will send CREATED in reponse !
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<String>("failure",HttpStatus.BAD_REQUEST);
    }


}
