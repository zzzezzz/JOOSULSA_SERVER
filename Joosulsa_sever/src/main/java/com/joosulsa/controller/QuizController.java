package com.joosulsa.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.entity.Tb_Point_Earn;
import com.joosulsa.entity.Tb_Quiz;
import com.joosulsa.entity.Tb_User;
import com.joosulsa.repository.PointEarnRepository;
import com.joosulsa.repository.QuizRepository;
import com.joosulsa.repository.UserRepository;

@RestController
@Transactional
public class QuizController {

	@Autowired
	private QuizRepository quizRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PointEarnRepository pointEarnRepo;

	@PostMapping("/quizRequest")
	public String quizDataCheck(String quizNum) {
		long longQuiz = Long.parseLong(quizNum);

		Tb_Quiz quizLoad = quizRepo.findByQuizNum(longQuiz);

		if (quizLoad != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				String jsonQuiz = objectMapper.writeValueAsString(quizLoad);
				return jsonQuiz;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return "JSON 변환 실패: " + e.getMessage();
			}
		} else {
			return "로그인 실패";
		}
	}

	@PostMapping("/quizPoint")
	public String quizPointRequest(String correctQuizNum, String correctUserId, String earnTime) {

		long quizNum = Long.parseLong(correctQuizNum);
		
		try {
			// 사용자 정보 가져오기
			Tb_User user = userRepo.findById(correctUserId).orElse(null);
			// 퀴즈 정보 가져오기
			Tb_Quiz quiz = quizRepo.findByQuizNum(quizNum);

			if (user != null && quiz != null) {
				
				user.setQuizParticipation(true);
	            userRepo.save(user);
				
				// 포인트 기록 생성
				Tb_Point_Earn pointEarn = new Tb_Point_Earn();
				pointEarn.setEarnPoint(quiz.getQuizPoint());
				pointEarn.setEarnedAt(earnTime);
				pointEarn.setUserId(user);
				pointEarn.setQuizNum(quiz);
				
				pointEarnRepo.save(pointEarn);

				return "Point added successfully!";
			} else {
				return "User or quiz not found!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error adding point!";
		}

	}

}
