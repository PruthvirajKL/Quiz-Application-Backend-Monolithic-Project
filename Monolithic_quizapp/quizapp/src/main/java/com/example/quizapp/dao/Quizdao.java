package com.example.quizapp.dao;

import com.example.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Quizdao extends JpaRepository<Quiz,Integer> {
}
