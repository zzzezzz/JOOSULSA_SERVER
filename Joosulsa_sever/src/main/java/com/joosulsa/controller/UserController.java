package com.joosulsa.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@PostMapping("/login")
	public String login(@RequestParam String userId, @RequestParam String userPw) {
		Tb_User loginCheck = userRepo.findByUserIdAndUserPw(userId, userPw);
		
		if(loginCheck != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				String jsonCheck = objectMapper.writeValueAsString(loginCheck);
				return jsonCheck;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return "JSON 변환 실패: " + e.getMessage();
			}
		}else {
			return "로그인 실패";
		}
		
		
		
		
	}

}
