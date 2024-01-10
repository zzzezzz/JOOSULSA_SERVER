package com.joosulsa.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		List<Tb_Recycling> result = searchRepo.findAll();
		

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
	public String photoData(String sendNum, String method, String userId, String earnTime) {
		System.out.println("사진 검색 요청 들어왔나?");
		Long photoNum = Long.parseLong(sendNum);
		System.out.println(photoNum + method);
		Tb_Recycling recyData = searchRepo.findByRecycleNumAndSearchMethod(photoNum, method);

		if (recyData != null) {
			System.out.println("??");
			int viewNum = recyData.getRecycleViews();
			viewNum += 1;
			System.out.println(recyData.getRecycleViews());
			recyData.setRecycleViews(viewNum);
			searchRepo.save(recyData);
			System.out.println(recyData.getRecycleViews());

			Tb_User userEntity = userRepo.findByUserId(userId);
			if (userEntity != null) {
				int pointsToAdd = recyData.getRecyclePoint(); // Tb_Recycling에서 가져온 포인트 지급량
				System.out.println("???");
				// Tb_Point_Earn에 데이터 추가
				Tb_Point_Earn pointEarn = new Tb_Point_Earn();
				pointEarn.setUserId(userEntity);
				pointEarn.setEarnPoint(pointsToAdd);
				pointEarn.setRecycleNum(recyData);
				pointEarn.setEarnedAt(earnTime);
				pointEarnRepo.save(pointEarn);
			}
			int totalPoints = userRepo.calculateTotalPoints(userId);
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("recyData", recyData);
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

	@PostMapping("/image")
	public String imageRequest(String recycleNum) {
	    if (recycleNum == null) {
	        return "recycleNum is null";
	    }

	    try {
	        long imgReq = Long.parseLong(recycleNum);
	        Tb_Recycling imgRecy = searchRepo.findByRecycleNum(imgReq);
	        String base64_sepa = null;
	        String base64_recy = null;

	        if (imgRecy != null) {
	            Map<String, Object> responseData = new HashMap<>();
	            try {
	                // 경로를 이용하여 이미지 가져오기
	                byte[] sepaImg = Files.readAllBytes(Paths.get(imgRecy.getSepaImg()));
	                byte[] recyImg = Files.readAllBytes(Paths.get(imgRecy.getRecycleImg()));

	                // Base64로 인코딩
	                base64_sepa = Base64.getEncoder().encodeToString(sepaImg);
	                base64_recy = Base64.getEncoder().encodeToString(recyImg);

	            } catch (Exception e) {
	                e.printStackTrace();
	                return "여기가 문제";
	            }
	            responseData.put("recyImg", base64_recy);
	            responseData.put("sepaImg", base64_sepa);
	            ObjectMapper objectMapper = new ObjectMapper();

	            // JSON 형태로 변환
	            String jsonCheck;
				try {
					jsonCheck = objectMapper.writeValueAsString(responseData);
					return jsonCheck;
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "여기가 문제2";
				}

	            // JSON 형태의 응답 전송
	            
	        } else {
	            return "No data found for recycleNum: " + imgReq;
	        }
	    } catch (NumberFormatException e) {
	        return "Invalid recycleNum format";
	    }
	}


}
