package com.joosulsa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.dto.RecycleDTO.cateData;
import com.joosulsa.repository.SearchRepository;

@RestController
@Transactional
public class HashController {
	
	// repo 연결
	@Autowired
	private SearchRepository searchRepo;
	
	@PostMapping("/hashtag")
	public List<cateData> cateData(){
		
		List<String> cateList = searchRepo.findTrashNames();

		List<cateData> cateNameList = new ArrayList<>();

		ObjectMapper objectMapper = new ObjectMapper();

		for (String data : cateList) {
			String trashName = (String) data;


			cateData cateData = new cateData();
			cateData.setTrashName(trashName);

			cateNameList.add(cateData);

		}
		
		//	System.out.println(cateNameList.get(0).toString());

		return cateNameList;
	}
		
		
	}
	
	
	
	
	

