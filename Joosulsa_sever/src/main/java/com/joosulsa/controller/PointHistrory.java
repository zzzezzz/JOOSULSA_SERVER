package com.joosulsa.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.joosulsa.repository.PointHistroryRepository;

@RestController
@Transactional
public class PointHistrory {

	@Autowired
	private PointHistroryRepository poRepo;
	
	
	
}
