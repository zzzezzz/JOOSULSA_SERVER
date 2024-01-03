package com.joosulsa.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.entity.Tb_Recycling;
import com.joosulsa.mapper.SearchMapper;
import com.joosulsa.repository.SearchRepository;

@RestController
@Transactional
public class SearchController {
	
	@Autowired
	private SearchMapper searchMapper;
	
	@Autowired
	private SearchRepository searchRepo;
	
	// 검색기능
	@PostMapping("/search")
	public String searchDate(String search) {
		Tb_Recycling searchCheck = searchRepo.findByTrashName(search);
		
		if(searchCheck != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				String Data = objectMapper.writeValueAsString(searchCheck);
				return Data;
			} catch (Exception e) {
				e.printStackTrace();
				return "JSON 변환 실패" + e.getMessage();
			}
		}else {
			return null;
		}
	}
//	@PostMapping("/searchImg")
//	public String searchImg(Sting )
	

}
