package com.joosulsa.controller;

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
import com.joosulsa.entity.Tb_User;
import com.joosulsa.mapper.UserMapper;
import com.joosulsa.repository.UserRepository;

@RestController
@Transactional
public class UserController {

//	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRepository userRepo;

//	// 생성자를 통해 PasswordEncoder 주입
//	public UserController(PasswordEncoder passwordEncoder) {
//		this.passwordEncoder = passwordEncoder;
//	}

	// 회원가입
	@PostMapping("/register")
	public String register(String name, String id, String pw, String nick, String address, String jointime) {
		System.out.println("aaaaa");
		System.out.println("user : " + name + id + pw + nick + address);

		Tb_User result = new Tb_User();
		result.setUserId(id);
		result.setJoinedAt(jointime);
		result.setUserAddr(address);
		result.setUserName(name);
		result.setUserNick(nick);
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

	@PostMapping("/myChange")
	public Tb_User myChange(String id, String newPw, String newNick, String newAddr) {
		Tb_User userChange = userRepo.findByUserId(id);
		int result = userRepo.myChange(id, newPw, newNick, newAddr);

		return userChange;
	}

}
