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


    // -- CREATE THE QUIZ
    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam Integer numQ,@RequestParam String title){
        return quizService.createQuiz(category,numQ,title);
    }


    // -- GET QUIZ BY ID
    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
         return quizService.getQuizQuestions(id);
    }

    // -- CALCULATE SCORE BY SUBMITTING QUIZ
    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id,responses);
    }

    // -- GET THE PERFORMANCE ANALYSIS WITH GEMINI API
    @PostMapping("/submit/analysis/{id}")
    public ResponseEntity<String> analyseQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.analyseQuiz(id,responses);
    }

}
