package com.example.quizapp.cotroller;

import com.example.quizapp.model.Questions;
import com.example.quizapp.service.Questionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class Questioncontroller {

    @Autowired
    Questionservice questionservice;

    @GetMapping("allquestions")
   public ResponseEntity<List<Questions>> getAllquestions(){
        return questionservice.getAllquestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Questions>> getquestionbycategory(@PathVariable String category){
        return questionservice.getquestionbycategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Questions questions){
        return questionservice.addQuestion(questions);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable int id,@RequestBody Questions questions){
        return questionservice.updateQuestion(id,questions);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id){
        return questionservice.deleteQuestion(id);
    }
}
