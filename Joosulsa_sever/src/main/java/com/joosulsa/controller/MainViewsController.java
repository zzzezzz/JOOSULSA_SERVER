package com.joosulsa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.dto.MainViewsDTO;
import com.joosulsa.dto.TownRankDTO.TownRankData;
import com.joosulsa.entity.Tb_Recycling;
import com.joosulsa.repository.MainViewsRepository;

@RestController
@Transactional
public class MainViewsController{

	// repo 연결
	@Autowired
	private MainViewsRepository mainViewsRepo;
	
	
	@PostMapping("/mainViews")
	public String mainViewsData() {
		
		List<String> mainViewsDataList = mainViewsRepo.findTrashNamesOrderedByTotalRecycleViewsDesc();
		
		// MainViewsDTO 객체의 리스트로 변환
        List<MainViewsDTO.mainViewsData> dtoList = new ArrayList<>(); // 담아줄 리스트
        
        for (String trashName : mainViewsDataList) {
            MainViewsDTO.mainViewsData dto = new MainViewsDTO.mainViewsData();
            dto.setTrashName(trashName);
            dtoList.add(dto);
        }
		
        // Java 객체의 리스트를 JSON 형태의 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonOutput = "";
        try {
            jsonOutput = objectMapper.writeValueAsString(dtoList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

		// System.out.println(jsonOutput);
        
		return jsonOutput;
		
	}
	
	
	
	
}
