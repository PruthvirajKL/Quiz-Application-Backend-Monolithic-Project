package com.example.quizapp.service;

import com.example.quizapp.dao.Questiondao;
import com.example.quizapp.model.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Questionservice {

    @Autowired
    Questiondao questiondao;

    public ResponseEntity<List<Questions>> getAllquestions() {
        try {
            return new ResponseEntity<>(questiondao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<List<Questions>> getquestionbycategory(String category) {
       try {
           return new ResponseEntity<>(questiondao.findBycategory(category),HttpStatus.OK);
       }catch (Exception e){
           e.printStackTrace();
       }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Questions questions) {
        try {
            questiondao.save(questions);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Question not added",HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> updateQuestion(int id, Questions questions) {
        try {
             Optional<Questions> findQuestion=questiondao.findById(id);
             if ((findQuestion.isPresent())){
                 Questions existQuestion=findQuestion.get();

                 existQuestion.setCategory(questions.getCategory());
                 existQuestion.setDifficultylevel(questions.getDifficultylevel());
                 existQuestion.setOption1(questions.getOption1());
                 existQuestion.setOption2(questions.getOption2());
                 existQuestion.setOption3(questions.getOption3());
                 existQuestion.setOption4(questions.getOption4());
                 existQuestion.setQuestion_title(questions.getQuestion_title());
                 existQuestion.setRight_answer(questions.getRight_answer());

                 questiondao.save(existQuestion);
                 return new ResponseEntity<>("Updated Successful",HttpStatus.OK);
             }else {
                 return new ResponseEntity<>("Question Not found",HttpStatus.NOT_FOUND);
             }

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error Question Not Found",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            Optional<Questions> findQuestion = questiondao.findById(id);
            if (findQuestion.isPresent()) {
                questiondao.deleteById(id);
                return new ResponseEntity<>("Deleted Successful", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
    }
}
