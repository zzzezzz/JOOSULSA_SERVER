package com.joosulsa.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.joosulsa.repository.PointHistroryRepository;

@Controller
@Transactional
public class PointHistrory {

	@Autowired
	private PointHistroryRepository poRepo;
	
	// 
	
	
}
