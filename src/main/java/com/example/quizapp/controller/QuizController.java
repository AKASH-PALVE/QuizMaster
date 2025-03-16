package com.example.quizapp.controller;

import com.example.quizapp.model.GeminiResponse;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Response;
import com.example.quizapp.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;



    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam Integer numQ,@RequestParam String title){
        // return new ResponseEntity<>("quiz create",HttpStatus.OK);
        // we need to return the quiz !
        // return quizService;

        return quizService.createQuiz(category,numQ,title);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){

        // For QuestionWrapper type , Return type : List<QuestionWrappper>
         return quizService.getQuizQuestions(id);

        // For Question type , Return type : List<Question>
//        return quizService.getQuiz(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id,responses);
    }

}
