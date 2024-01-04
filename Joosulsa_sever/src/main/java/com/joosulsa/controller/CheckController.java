package com.joosulsa.controller;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.entity.Tb_Attendance;
import com.joosulsa.entity.Tb_Point_Earn;
import com.joosulsa.entity.Tb_Quiz;
import com.joosulsa.entity.Tb_User;
import com.joosulsa.repository.CheckRepository;
import com.joosulsa.repository.PointEarnRepository;
import com.joosulsa.repository.UserRepository;

@RestController
@Transactional
public class CheckController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CheckRepository checkRepo;
	
	@Autowired
	private PointEarnRepository pointEarnRepo;
	
	@PostMapping("/checkPoint")
	public String checkPointRequest(String autoId, String monthlyAtt, String earnTime) {
		System.out.println("check1");
		long userAtt = Long.parseLong(monthlyAtt);
		int userAttNum = Integer.parseInt(monthlyAtt);
		try {
			// 사용자 정보 가져오기
			Tb_User user = userRepo.findById(autoId).orElse(null);
			// 퀴즈 정보 가져오기
			Tb_Attendance att = checkRepo.findByAttNum(userAtt);
			System.out.println("check2");
			if (user != null && att != null) {
				
				// 유저 출석 처리
				user.setAttendance(true);
				user.setMonthlyAttendance(userAttNum);
	            userRepo.save(user);
				System.out.println("check3");
				// 포인트 기록 생성
				Tb_Point_Earn pointEarn = new Tb_Point_Earn();
				pointEarn.setEarnPoint(att.getAttPoint());
				pointEarn.setEarnedAt(earnTime);
				pointEarn.setUserId(user);
				pointEarn.setAttNum(att);
				
				pointEarnRepo.save(pointEarn);
				boolean userCheck = user.getAttendance;
				int totalPoints = userRepo.calculateTotalPoints(user.getUserId());

                // 응답 데이터 설정
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("message", "Point added successfully!");
                responseData.put("totalPoints", totalPoints);
                responseData.put("userCheck", userCheck);
                
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
	
	
	
	
	
	
}
