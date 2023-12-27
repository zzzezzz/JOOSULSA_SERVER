package com.joosulsa.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.entity.Tb_Quiz;
import com.joosulsa.repository.QuizRepository;

@RestController
@Transactional
public class QuizController {
	
	@Autowired
	private QuizRepository quizRepo;
	
	@PostMapping("/quizRequest")
	public String quizDataCheck(String quizNum) {
		long longQuiz = Long.parseLong(quizNum);
		
		Tb_Quiz quizLoad = quizRepo.findByQuizNum(longQuiz);
		
		if(quizLoad != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				String jsonQuiz = objectMapper.writeValueAsString(quizLoad);
				return jsonQuiz;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return "JSON 변환 실패: " + e.getMessage();
			}
		}else {
			return "로그인 실패";
		}
	}
	
}
