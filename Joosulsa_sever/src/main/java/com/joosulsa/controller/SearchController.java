package com.joosulsa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.dto.RecycleDTO.cateData;
import com.joosulsa.dto.UserRankDTO.UserRankData;
import com.joosulsa.entity.Tb_Point_Earn;
import com.joosulsa.entity.Tb_Recycling;
import com.joosulsa.entity.Tb_User;
import com.joosulsa.mapper.SearchMapper;
import com.joosulsa.repository.PointEarnRepository;
import com.joosulsa.repository.SearchRepository;
import com.joosulsa.repository.UserRepository;

@RestController
@Transactional
public class SearchController {

	@Autowired
	private SearchMapper searchMapper;

	@Autowired
	private SearchRepository searchRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PointEarnRepository pointEarnRepo;

	// 검색기능
	@PostMapping("/search")
	public String searchDate(String search, String method, String user, String earnTime) {
		System.out.println("?");
		System.out.println(method);
		Tb_Recycling searchCheck = searchRepo.findByTrashNameAndSearchMethod(search, method);

		if (searchCheck != null) {
			System.out.println("??");
			int viewNum = searchCheck.getRecycleViews();
			viewNum += 1;
			System.out.println(searchCheck.getRecycleViews());
			searchCheck.setRecycleViews(viewNum);
			searchRepo.save(searchCheck);
			System.out.println(searchCheck.getRecycleViews());

			Tb_User userEntity = userRepo.findByUserId(user);
			if (userEntity != null) {
				int pointsToAdd = searchCheck.getRecyclePoint(); // Tb_Recycling에서 가져온 포인트 지급량
				System.out.println("???");
				// Tb_Point_Earn에 데이터 추가
				Tb_Point_Earn pointEarn = new Tb_Point_Earn();
				pointEarn.setUserId(userEntity);
				pointEarn.setEarnPoint(pointsToAdd);
				pointEarn.setRecycleNum(searchCheck);
				pointEarn.setEarnedAt(earnTime);
				pointEarnRepo.save(pointEarn);
			}
			int totalPoints = userRepo.calculateTotalPoints(user);
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("searchCheck", searchCheck);
			responseData.put("totalPoints", totalPoints);
			ObjectMapper objectMapper = new ObjectMapper();
			System.out.println("????");
			try {
				String Data = objectMapper.writeValueAsString(responseData);
				System.out.println("?????");
				return Data;
			} catch (Exception e) {
				e.printStackTrace();
				return "JSON 변환 실패" + e.getMessage();
			}
		} else {
			return null;
		}
	}
//	@PostMapping("/searchImg")
//	public String searchImg(Sting )

	@PostMapping("/category")
	public List<cateData> categoryName() {

		List<String> cateList = searchRepo.findTrashNames();

		List<cateData> cateNameList = new ArrayList<>();

		ObjectMapper objectMapper = new ObjectMapper();

		for (String data : cateList) {
			String trashName = (String) data;

			System.out.println("User Nick: " + trashName);

			cateData cateData = new cateData();
			cateData.setTrashName(trashName);

			cateNameList.add(cateData);

		}

		return cateNameList;
	}

	@PostMapping("/photoData")
	public String photoData(String photoNum) {
		long longNum = Long.parseLong(photoNum);
		Tb_Recycling recyData = searchRepo.findByRecycleNum(longNum);

		if (recyData != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				String jsonPhoto = objectMapper.writeValueAsString(recyData);
				return jsonPhoto;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return "JSON 변환 실패: " + e.getMessage();
			}
		} else {
			return "뭔가 잘못되었다.";
		}
	}

}
