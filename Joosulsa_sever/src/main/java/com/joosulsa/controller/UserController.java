package com.joosulsa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.dto.UserRankDTO.UserRankData;
import com.joosulsa.entity.Tb_User;
import com.joosulsa.mapper.UserMapper;
import com.joosulsa.repository.PointEarnRepository;
import com.joosulsa.repository.UserRepository;

@RestController
@Transactional
public class UserController {

//	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PointEarnRepository earnRepo;

//	// 생성자를 통해 PasswordEncoder 주입
//	public UserController(PasswordEncoder passwordEncoder) {
//		this.passwordEncoder = passwordEncoder;
//	}

	// 회원가입
	@PostMapping("/register")
	public String register(String nick, String address, String jointime, String pw, String name, String id) {
		System.out.println("aaaaa");
		System.out.println("user : " + name + id + pw + nick + address);

		Tb_User result = new Tb_User();
		result.setUserId(id);
		result.setJoinedAt(jointime);
		result.setUserAddr(address);
		result.setUserName(name);
		result.setUserNick(nick);
		result.setAttendance(false);
		result.setQuizParticipation(false);
		// 비밀번호 암호화
		result.setUserPw(pw);

		userRepo.save(result);

		System.out.println("result=" + result);

		return "success";
	}

	// 회원가입 아이디 중복체크
	@PostMapping("/check/{userId}")
	public ResponseEntity<String> checkUserIdAvailability(@PathVariable String userId) {
		Tb_User existingUser = userRepo.findByUserId(userId);

		if (existingUser != null) {
			// 이미 존재하는 아이디인 경우
			return new ResponseEntity<>("DUPLICATED", HttpStatus.OK);
		} else {
			// 존재하지 않는 아이디인 경우
			return new ResponseEntity<>("AVAILABLE", HttpStatus.OK);
		}
	}

	// 로그인
	@PostMapping("/login")
	public String login(@RequestParam String userId, @RequestParam String userPw) {
		Tb_User loginCheck = userRepo.findByUserIdAndUserPw(userId, userPw);

		if (loginCheck != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				String jsonCheck = objectMapper.writeValueAsString(loginCheck);
				return jsonCheck;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return "JSON 변환 실패: " + e.getMessage();
			}
		} else {
			return "로그인 실패";
		}
	}

	// 회원 정보 변경
	@PostMapping("/myChange")
	public Tb_User myChange(String id, String newPw, String newNick, String newAddr) {

		System.out.println("이거 뭐냐");

		Tb_User userChange = userRepo.findByUserId(id);
		int result = userRepo.myChange(id, newPw, newNick, newAddr);

		return userChange;
	}

	// 자동로그인 유저 출석, 퀴즈 여부 불러오기
	@PostMapping("/dataCheck")
	public String dataCheck(String userId) {
		Tb_User dataCheck = userRepo.findByUserId(userId);

		if (dataCheck != null) {
			
			Integer totalPoints = userRepo.calculateTotalPoints(dataCheck.getUserId());
			Map<String, Object> responseData = new HashMap<>();
			if(totalPoints!= null) {
				responseData.put("totalPoints", totalPoints);
			}else {
				responseData.put("totalPoints", 0);
			}
			
			int monthlyAttendance = dataCheck.getMonthlyAttendance();
			boolean attendance = dataCheck.getAttendance;
			boolean quizParticipation = dataCheck.getQuizParticipation;
			
            
            responseData.put("quizBoolean", quizParticipation);
            responseData.put("checkBoolean", attendance);
            responseData.put("monthlyAttendance", monthlyAttendance);
            
            ObjectMapper objectMapper = new ObjectMapper();
            
            // JSON 형태로 변환
            String jsonCheck;
			try {
				jsonCheck = objectMapper.writeValueAsString(responseData);
				
				 // JSON 형태의 응답 전송
	            return jsonCheck;
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "뭔가 잘못됨";
			}

           
		} else {
			return "로그인 실패";
		}

	}
	
	
	@PostMapping("/makeFalse")
	public void makeFalse(String userId) {
		Tb_User makeFalse = userRepo.findByUserId(userId);
		
		if(makeFalse!= null) {
			makeFalse.setAttendance(false);
			userRepo.save(makeFalse);
		}
	}
	
	@PostMapping("/makeTrue")
	public void makeTrue(String userId) {
		Tb_User makeTrue = userRepo.findByUserId(userId);
		
		if(makeTrue!= null) {
			makeTrue.setAttendance(true);
			userRepo.save(makeTrue);
		}
	}

	// 출석체크 페이지 출석 현황 뿌리기
	@PostMapping("/checkOutput")
	public String checkOutput(String userId) {

		Tb_User checkOutput = userRepo.findByUserId(userId);

		if (checkOutput != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				int totalPoints = userRepo.calculateTotalPoints(checkOutput.getUserId());
				String infoUserId = checkOutput.getUserId();
				String infoUserNick = checkOutput.getUserNick();
				int monthlyAtt = checkOutput.getMonthlyAttendance();

				// 응답 데이터 설정
				Map<String, Object> responseData = new HashMap<>();
				responseData.put("message", "Point added successfully!");
				responseData.put("totalPoints", totalPoints);
				responseData.put("userNick", infoUserNick);
				responseData.put("userId", infoUserId);
				responseData.put("monthlyAttendance", monthlyAtt);

				// JSON 형태로 변환
				String jsonResponse = objectMapper.writeValueAsString(responseData);

				// JSON 형태의 응답 전송
				return jsonResponse;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return "JSON 변환 실패: " + e.getMessage();
			}
		} else {
			return "로그인 실패";
		}

	}
	
	@PostMapping("/personRank")
	public List<UserRankData> personRankData() {
		
		List<Object[]> personData = earnRepo.findTop10UsersTotalPoints();
		List<UserRankData> userRankList = new ArrayList<>();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		System.out.println("?????????????");
		
		for (Object[] data : personData) {
		    String userNick = (String) data[0];
		    Long totalPoints = (Long) data[1];
		    
		    System.out.println("User Nick: " + userNick + ", Total Points: " + totalPoints);
		    
		    UserRankData userRankData = new UserRankData();
            userRankData.setUserNick(userNick);
            userRankData.setTotalPoints(totalPoints);

            userRankList.add(userRankData);
		    
		}
		
		return userRankList;
	}
	
	@PostMapping("/userRankPrint")
	public String userRankPrint(String userId) {
		
		Tb_User user = userRepo.findByUserId(userId);
		
		if(user != null) {
			System.out.println("랭킹용 id 잘 나오나?" + user.getUserId());
			int rank = earnRepo.findUserRankByTotalPoints(user.getUserId()) + 1;
			String stringRank = String.valueOf(rank);

            // 직접 JSON 문자열로 응답 생성
            return "{\"rank\": \"" + stringRank + "\"}";
			
		}
		return "userRankPrint 안된다아=======================================================================";
		
	}


}
