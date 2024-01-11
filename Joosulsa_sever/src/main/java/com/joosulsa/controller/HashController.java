package com.joosulsa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.dto.RecycleDTO.cateData;
import com.joosulsa.entity.Tb_Recycling;
import com.joosulsa.repository.SearchRepository;

@RestController
@Transactional
public class HashController {
	
	// repo 연결
	@Autowired
	private SearchRepository searchRepo;
	
	// 해시태그 버튼 클릭 시 처리해주는 곳
	@PostMapping("/hashtag")
	public String hashRecy(String data, String method){
		
		Tb_Recycling hashRecy = searchRepo.findByTrashNameAndSearchMethod(data, method);
		System.out.println(hashRecy + "===================================================================");
		if(hashRecy!=null) {
			ObjectMapper objectMapper = new ObjectMapper(); // json 변환
			try {
				String hashRecyData = objectMapper.writeValueAsString(hashRecy); // String으로 변환 
				System.out.println(hashRecyData + "============================================================================================");
				return hashRecyData;
				
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return "오류나면...옴";
	}	
}
	
	
	
	
	

