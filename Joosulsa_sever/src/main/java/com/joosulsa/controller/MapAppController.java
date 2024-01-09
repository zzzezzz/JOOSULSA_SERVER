package com.joosulsa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.dto.TownRankDTO.TownRankData;
import com.joosulsa.dto.UserRankDTO.UserRankData;
import com.joosulsa.dto.UserTownPointDTO;
import com.joosulsa.dto.UserTownPointDTO.UserTownPointData;
import com.joosulsa.repository.TownRepository;

// 안드로이드 연결 시 필요한 컨트롤러
@RestController
//영속성 오류 해결 어노테이션
@Transactional
public class MapAppController {

	// 값이 없어도 repo 연결
		@Autowired
		private TownRepository townRepo;
	
		
		
		@PostMapping("/townList")
		public List<TownRankData> townRankData() {
			
			List<Object[]> townData = townRepo.findSumOfEarnPointGroupByTownName();
			List<TownRankData> townDataList = new ArrayList<>(); // 최종적으로 보내줄 리스트
			
			// Java 객체를 JSON 문자열로 변환하거나 반대로 JSON 문자열을 Java 객체로 변환하는 데 사용
			ObjectMapper objectMapper = new ObjectMapper();
			
			for (Object[] data : townData) {
			    String townName = (String) data[0];
			    System.out.println("값이 오냐고"+townName);
			    Long totalPoints = (Long)data[1];
			    System.out.println("값이 넘어오나?"+ totalPoints);
			    System.out.println("동네이름 " + townName + ", 동네 총 포인트: " + totalPoints);
			    
			    TownRankData townRankData = new TownRankData();
			    
			    townRankData.setTownName(townName);
			    townRankData.setTotalPoints(totalPoints);
			    
	            townDataList.add(townRankData);
			    
			}
			
			
			System.out.println(townDataList);
			return townDataList;
		}
		
		
		// 사용자가 속한 동네의 포인트를 가지고 오는 처리
		@PostMapping("/userTownPoint")
		public int userTownPoints(String userId){
			
			System.out.println("왜 안돼?");
			
			int totalPoints; // totalPoints 변수를 먼저 선언
			
			if (townRepo.getTotalEarnPointByUserIds(userId) == null) {
	        	// townRepo.calculateTotalPointsByTownNum(townNum)가 null이면 totalPoints에 0을 할당
	        	// townRepo.calculateTotalPointsByTownNum(townNum)가 String이기 때문에 null이 됨
	            totalPoints = 0;
	            
	        } else {
	        	// null이 아니면 해당 값을 할당
	            totalPoints = Integer.parseInt(townRepo.getTotalEarnPointByUserIds(userId));
	            System.out.println( "총 포인트: "+ totalPoints);
	        }
		
			
			return totalPoints;
		}
		
	
	
}
