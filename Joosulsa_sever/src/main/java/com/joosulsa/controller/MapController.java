package com.joosulsa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.dto.TownRankDTO.TownRankData;
import com.joosulsa.dto.UserRankDTO.UserRankData;
import com.joosulsa.entity.Tb_Town;
import com.joosulsa.repository.TownRepository;

@Controller
// 영속성 오류 해결 어노테이션
@Transactional
public class MapController {

	// 값이 없어도 repo 연결
	@Autowired
	private TownRepository townRepo;
	
	
	// 웹 링크 연결
	@PostMapping("/map")
	public String goMap(Model model, String userId) {
		 
		
//		System.out.println("모델에 뭐가 담겨?" + model);
//		List<Tb_Town> townList = townRepo.findAll();
//		// jsp에서 요청한 정보 > Tb_Town에 있는 모든 데이터
//		// findAll() > 매개변수가 필요없는 메서드
//		// 그래서 repo를 가지고와서 findAll 메서드 처리해주고
//		// Tb_Town에 있는 데이터는 어떤 형태인지 확인할 것 > Repo파일로 가서 extends … 확인
//		// <Tb_Town, String> < 이렇게 작성되어 있음
//		// 그래서 자료형은 Tb_Town 인것을 알 수 있음
//		// 그러니까 Tb_Town을 List로 묶어주자...
//		
//		// 근데 이렇게 작성했을때 ? townList의 콘솔창을 확인해보면 Tb_Town만 출력된다.
//		// System.out.println(townList+"1");
//		// 이 현상을 확인하기 위해서 Tb_Town 파일을 확인
//		// 확인해보니 toString 메서드에 리턴값으로 Tb_Town이 입력되어서 발생한 문제
//		// 따라서 Tb_Town의 toString 메서드를 변경시켜주자.
//		
//		//		// 첫 번째 townName을 선택하여 model에 추가하는 법
//		//	    String firstTownName = townList.isEmpty() ? "" : townList.get(0).getTownName();
//		//	    model.addAttribute("townName", firstTownName);
//
//		
//		Map<String, String> townTotalPointsMap = new HashMap<>(); // townNum과 totalPoints를 매핑할 Map 생성
//		//  int 형으로 보내니 오류가 나서 각 동네 번호에 맞는 포인트 총합이 출력이 안되는 것같아서 String으로 형변환 
//		
//		for (Tb_Town town : townList) {
//		    Long townNum = town.getTownNum(); // townNum을 문자열로 변환
//		    
//		    // 동네 번호가 null일때 처리 로직
//		    if (townNum == null) {
//		    	System.out.println("townNumStr 값이 null이거나 빈 문자열입니다. townNumStr: " + townNum);
//		        continue; // 현재 반복을 건너뜁니다.
//		    }
//
//		    
//		    // townNum을 인자로 사용하여 totalPoints 조회
//		    try {
//		    	// sql에서 동네 번호를 기준으로 집계함수 sum을 사용했을 시 동네에 해당하는 점수가 없어 합산해줄 수 있는 값이 없을 경우 오류 발생
//		    	// -> int totalPoints 에서 int가 null을 받을 수 없기 때문에 생기는 문제
//		    	// -> 그래서 sql 메소드 처리시 sum으로 집계했을때 값이 null 일 경우 포인트 총합을 0으로 처리해주는 로직이 필요
//		    	
//		    	int totalPoints; // totalPoints 변수를 먼저 선언
//		    	
//
//		        
//		    	// townRepo에서 calculateTotalPointsByTownNum를 String 값으로 가져온다.
//		    	// 이 처리를 해주는 이유는 null은 int로 받을 수 없기 때문에 쿼리문에서 String으로 지정 
//		        if (townRepo.calculateTotalPointsByTownNum(townNum) == null) {
//		        	// townRepo.calculateTotalPointsByTownNum(townNum)가 null이면 totalPoints에 0을 할당
//		        	// townRepo.calculateTotalPointsByTownNum(townNum)가 String이기 때문에 null이 됨
//		            totalPoints = 0;
//		            
//		        } else {
//		        	// null이 아니면 해당 값을 할당
//		        	// 
//		            totalPoints = Integer.parseInt(townRepo.calculateTotalPointsByTownNum(townNum));
//		            System.out.println( "총 포인트: "+ totalPoints);
//		        }
//		        
//		        // townNum을 문자열로 변환하여 townTotalPointsMap에 포인트 총합을 저장
//		        townTotalPointsMap.put(String.valueOf(townNum), String.valueOf(totalPoints)); 
//		        // Map에 키값과 value값이 각각 String으로 지정되어 있어서 String으로 변환해줌
//		        
//		        System.out.println("각 동네별 포인트 총합 : "+ townTotalPointsMap);
//	            
//		    } catch (Exception e) {
//		        // 예외 처리: 쿼리 호출 시 문제가 발생한 경우
//		        System.out.println("총합포인트 조회 중 오류 발생: " + e.getMessage());
//		        e.printStackTrace(); // 에러 로그 출력
//		    }
//		}
		
		System.out.println("값을 받아오나?" + userId);
		
		List<Object[]> townGpsData = townRepo.getTownCoordinatesByUserId(userId);
		List<TownRankData> townGpsDataList = new ArrayList<>(); // 최종적으로 보내줄 리스트
		// 참조 DTO <TownRankData>
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		System.out.println(townGpsData);
		
		for (Object[] data : townGpsData) {
		    String townX = (String) data[0];
		    System.out.println("좌표 값 X"+townX);
		    String townY = (String)data[1];
		    System.out.println("좌표 값 Y"+ townY);
		    
		    TownRankData townRankData = new TownRankData();
		    
		    townRankData.setTownX(townX);
		    townRankData.setTownY(townX);
		   
		    
		    townGpsDataList.add(townRankData);
		    
		}
		
		model.addAttribute("townGpsDataList", townGpsDataList);
		
		System.out.println("좌표 값 넘어오나" + townGpsDataList);
		
		
		
//		
//		model.addAttribute("townList", townList); // townList라는 이름으로 jsp에 보냄
//		model.addAttribute("townTotalPointsMap", townTotalPointsMap);  // townNum과 totalPoints 매핑 정보를 JSP로 보냄
//		
//		System.out.println(townList);
//		System.out.println(townTotalPointsMap);
		
		return "map";
	}
	
	
	
}
