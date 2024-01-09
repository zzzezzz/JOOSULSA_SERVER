package com.joosulsa.controller;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.entity.Tb_Point_Earn;
import com.joosulsa.entity.Tb_Quiz;
import com.joosulsa.entity.Tb_Town;
import com.joosulsa.entity.Tb_User;
import com.joosulsa.repository.PointEarnRepository;
import com.joosulsa.repository.QuizRepository;
import com.joosulsa.repository.TownRepository;
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
	
	@Autowired
	private TownRepository townRepo;

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
		System.out.println("여긴가?"+correctUserId);
		try {
			// 사용자 정보 가져오기
			Tb_User user = userRepo.findById(correctUserId).orElse(null);
			// 퀴즈 정보 가져오기
			Tb_Quiz quiz = quizRepo.findByQuizNum(quizNum);
			System.out.println("그럼 여긴가?"+user);
			if (user != null && quiz != null) {
				// 동네 정보 가져오기
	            Tb_Town town = findTownByUserAddress(user.getUserAddr());
	            System.out.println("아니면 여긴가?"+town);
				user.setQuizParticipation(true);
	            userRepo.save(user);
	            
				// 포인트 기록 생성
				Tb_Point_Earn pointEarn = new Tb_Point_Earn();
				pointEarn.setEarnPoint(quiz.getQuizPoint());
				pointEarn.setEarnedAt(earnTime);
				pointEarn.setUserId(user);
				pointEarn.setQuizNum(quiz);
				pointEarn.setTownNum(town);
				System.out.println(pointEarn);
				pointEarnRepo.save(pointEarn);
				boolean userQuizCheck = user.getQuizParticipation;
				int totalPoints = userRepo.calculateTotalPoints(user.getUserId());

				// 응답 데이터 설정
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("message", "Point added successfully!");
                responseData.put("totalPoints", totalPoints);
                responseData.put("userCheck", userQuizCheck);
                
                ObjectMapper objectMapper = new ObjectMapper();
                
                // JSON 형태로 변환
                String jsonResponse = objectMapper.writeValueAsString(responseData);

                // JSON 형태의 응답 전송
                return jsonResponse;
			} else {
				return "User or quiz not found!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error adding point!";
		}

	}

	private Tb_Town findTownByUserAddress(String userAddr) {
		// 주소에서 동네 정보 추출
	    String[] addressParts = userAddr.split(" ");
	    System.out.println(userAddr);
	    // 동네 정보가 있는지 확인
	    if (addressParts.length >= 2) {
	        String townName = addressParts[2]; // 3번째 단어를 동네로 간주
	        System.out.println(townName);
	        // 동네 이름으로 Tb_Town을 찾아 반환
	        return townRepo.findByTownName(townName);
	    } else {
	        // 동네 정보가 없으면 null 반환 또는 적절한 로직 추가
	        return null;
	    }
	}


}
