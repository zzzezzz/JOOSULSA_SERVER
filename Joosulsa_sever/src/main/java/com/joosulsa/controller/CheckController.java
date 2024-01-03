package com.joosulsa.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joosulsa.entity.Tb_Attendance;
import com.joosulsa.entity.Tb_Point_Earn;
import com.joosulsa.entity.Tb_Quiz;
import com.joosulsa.entity.Tb_User;
import com.joosulsa.repository.CheckRepository;
import com.joosulsa.repository.UserRepository;

@RestController
@Transactional
public class CheckController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CheckRepository checkRepo;
	
	@PostMapping("/checkPoint")
	public String checkPointRequest(String autoId, String monthlyAtt) {
		
		
		try {
			// 사용자 정보 가져오기
			Tb_User user = userRepo.findById(autoId).orElse(null);
			// 퀴즈 정보 가져오기
			Tb_Attendance att = checkRepo.findByAttNum()

			if (user != null && quiz != null) {
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
