package com.joosulsa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joosulsa.entity.Tb_Town;
import com.joosulsa.repository.TownRepository;

// 안드로이드 스프링 통신 시 사용하는 어노테이션
@Controller
// 영속성 오류 해결 어노테이션
@Transactional
public class MapController {

	// 값이 없어도 repo 연결
	@Autowired
	private TownRepository townRepo;
	
	
	// 웹 링크 연결
	@RequestMapping("/map")
	public String goMap(Model model) {
//		System.out.println(model);
		List<Tb_Town> townList = townRepo.findAll();
		// jsp에서 요청한 정보 > Tb_Town에 있는 모든 데이터
		// findAll() > 매개변수가 필요없는 메서드
		// 그래서 repo를 가지고와서 findAll 메서드 처리해주고
		// Tb_Town에 있는 데이터는 어떤 형태인지 확인할 것 > Repo파일로 가서 extends … 확인
		// <Tb_Town, String> < 이렇게 작성되어 있음
		// 그래서 자료형은 Tb_Town 인것을 알 수 있음
		// 그러니까 Tb_Town을 List로 묶어주자...
		
		// 근데 이렇게 작성했을때 ? townList의 콘솔창을 확인해보면 Tb_Town만 출력된다.
		// System.out.println(townList+"1");
		// 이 현상을 확인하기 위해서 Tb_Town 파일을 확인
		// 확인해보니 toString 메서드에 리턴값으로 Tb_Town이 입력되어서 발생한 문제
		// 따라서 Tb_Town의 toString 메서드를 변경시켜주자.
		
		//		// 첫 번째 townName을 선택하여 model에 추가하는 법
		//	    String firstTownName = townList.isEmpty() ? "" : townList.get(0).getTownName();
		//	    model.addAttribute("townName", firstTownName);

		
		Map<String, Integer> townTotalPointsMap = new HashMap<>(); // townNum과 totalPoints를 매핑할 Map 생성

		for (Tb_Town town : townList) {
		    String townNumStr = town.getTownNum().toString(); // townNum을 문자열로 변환
		    System.out.println("데이터 넘어오는지 확인: " + townNumStr);
		 // townNumStr이 null이거나 빈 문자열일 경우 처리
		    if (townNumStr == null || townNumStr.isEmpty()) {
		        System.out.println("townNumStr 값이 null이거나 빈 문자열입니다. townNumStr: " + townNumStr);
		        continue; // 현재 반복을 건너뜁니다.
		    }

		    System.out.println("데이터 넘어오는지 확인: " + townNumStr);
		    
		    // townNumStr을 인자로 사용하여 totalPoints 조회
		    try {
		        Integer totalPoints = townRepo.calculateTotalPointsByTownNum(townNumStr);
		        System.out.println("총합포인트가 넘어오는지 확인: " + totalPoints);
		    } catch (Exception e) {
		        // 예외 처리: 쿼리 호출 시 문제가 발생한 경우
		        System.out.println("총합포인트 조회 중 오류 발생: " + e.getMessage());
		        e.printStackTrace(); // 에러 로그 출력
		        // 추가적인 예외 처리 로직을 여기에 작성할 수 있습니다.
		    }
		}
		

		
		
		
		model.addAttribute("townList", townList); // townList라는 이름으로 jsp에 보냄 
		model.addAttribute("townTotalPointsMap", townTotalPointsMap);  // townNum과 totalPoints 매핑 정보를 JSP로 보냄
		
       
		
		
		
		return "map";
	}
	
	
}
