package com.joosulsa.controller;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 안드로이드 스프링 통신 시 사용하는 어노테이션
@Controller
// 영속성 오류 해결 어노테이션
@Transactional
public class MapController {

	
	// 웹 링크 연결
	@RequestMapping("/map")
	public String goMap() {
		return "map";
	}
	
	
}
