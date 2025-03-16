package com.example.quizapp.dao;


import com.example.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM question q WHERE q.category =:category ORDER BY RANDOM() LIMIT :numQ",nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, Integer numQ);

    //  @Query(value = "SELECT * FROM quiz_questions q WHERE q.quiz_id = :id",nativeQuery = true)
    //  List<Question> getQuestionsById(Integer id);
    //  No Need

}
