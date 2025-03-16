package com.example.quizapp.controller;

import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){

        // return "drum roll... Your application started, here are your questions ! ";
        // return "<h1>drum roll... Your application started, here are your questions ! </h1>";
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    // if the path variable and parameter is diff
//    @GetMapping("category/{cat}")
//    public List<Question> getQestionsByCategory(@PathVariable("cat") String category){
//        return questionService.getQuestionsByCategory(category);
//    }

    @PostMapping("/add")
    public ResponseEntity<String>  addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }
    // delete question

    @DeleteMapping("/delete")
    public  ResponseEntity<String> deleteQuestion(@RequestBody Question question){
        return questionService.deleteQuestion(question);
    }
    /// try with just id !


    // Full update
    @PutMapping("/update")
    public  ResponseEntity<String> updateQuestion(@RequestBody Question question){
        return questionService.updateQuestion(question);
    }

    // Partial Update

}


