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

    // -- GET ALL QUESTIONS

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    // -- GET ALL QUESTIONS BY CATEGORY

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    // -- GET ALL QUESTIONS BY DIFFICULTY

    @GetMapping("difficulty/{difficulty}")
    public ResponseEntity<List<Question>> getQestionsByDifficulty(@PathVariable String difficulty){
        return questionService.getQuestionsByDifficulty(difficulty);
    }



    // -- ADD QUESTION
    @PostMapping("/add")
    public ResponseEntity<String>  addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    // -- DELETE QUESTION
    @DeleteMapping("/delete")
    public  ResponseEntity<String> deleteQuestion(@RequestBody Question question){
        return questionService.deleteQuestion(question);
    }


    // -- UPDATE QUESTION
    @PutMapping("/update")
    public  ResponseEntity<String> updateQuestion(@RequestBody Question question){
        return questionService.updateQuestion(question);
    }

}


