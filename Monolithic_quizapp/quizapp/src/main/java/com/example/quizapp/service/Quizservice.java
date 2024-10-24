package com.example.quizapp.service;

import com.example.quizapp.dao.Questiondao;
import com.example.quizapp.dao.Quizdao;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Questions;
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
public class Quizservice {

    @Autowired
    Quizdao quizdao;
    @Autowired
    Questiondao questiondao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Questions> questions=questiondao.findRandomQuestionsByCategory(category,numQ);


        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizdao.save(quiz);

        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(int id) {
        Optional<Quiz> quiz=quizdao.findById(id);
        List<Questions> questionsfromDB=quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser=new ArrayList<>();
        for (Questions q:questionsfromDB){
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestion_title(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionForUser.add(qw);
        }
        return new ResponseEntity<>(questionForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz=quizdao.findById(id).get();
        List<Questions> questions=quiz.getQuestions();
        int right=0;
        int i=0;
        for(Response response : responses){
            if (response.getResponse().equals(questions.get(i).getRight_answer())){
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
