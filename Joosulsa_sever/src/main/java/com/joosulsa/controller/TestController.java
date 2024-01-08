package com.joosulsa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
// Controller (컨트롤러)
import org.springframework.web.bind.annotation.RequestMapping;

import com.joosulsa.model.PredictionResult;
@Controller // Controller 이므로 Controller 어노테이션 정의
public class TestController {
	
	@RequestMapping("/predict")
	private void predict(@RequestBody PredictionResult predictionResult) {
		System.out.println(predictionResult);
	}

	
	
}
