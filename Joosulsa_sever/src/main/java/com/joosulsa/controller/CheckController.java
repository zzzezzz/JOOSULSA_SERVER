package com.joosulsa.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joosulsa.repository.CheckRepository;
import com.joosulsa.repository.UserRepository;

@RestController
@Transactional
public class CheckController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CheckRepository checkRepo;
	
//	@PostMapping("/checkPoint")
//	public String checkPointRequest(String autoId) {
//		
//	}
	
	
	
	
	
	
}
