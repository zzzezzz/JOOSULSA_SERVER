package com.joosulsa.controller;

import java.io.File;
import java.io.IOException;
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
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.joosulsa.dto.RecycleDTO.cateData;
import com.joosulsa.dto.UserRankDTO.UserRankData;
import com.joosulsa.entity.Tb_Point_Earn;
import com.joosulsa.entity.Tb_Recycling;
import com.joosulsa.entity.Tb_Town;
import com.joosulsa.entity.Tb_User;
import com.joosulsa.mapper.SearchMapper;
import com.joosulsa.repository.PointEarnRepository;
import com.joosulsa.repository.PointHistroryRepository;
import com.joosulsa.repository.SearchRepository;
import com.joosulsa.repository.TownRepository;
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

	@Autowired
	private TownRepository townRepo;
	
	@Autowired
	private PointHistroryRepository hisRepo;

	// 검색기능
	@PostMapping("/search")
	public String searchDate(String search, String method, String user, String earnTime) {
		System.out.println(
				"===============================================================================================");
		System.out.println(search);
		Tb_Recycling searchCheck = searchRepo.findByTrashNameAndSearchMethod(search, method);

		if (searchCheck != null) {
			System.out.println(
					"1111===============================================================================================");
			int viewNum = searchCheck.getRecycleViews();
			viewNum += 1;
			searchCheck.setRecycleViews(viewNum);
			searchRepo.save(searchCheck);

			Tb_User userEntity = userRepo.findByUserId(user);
			if (userEntity != null) {
				System.out.println(
						"2222===============================================================================================");
				Tb_Town town = findTownByUserAddress(userEntity.getUserAddr()); // 유저 주소에 해당하는 타운넘버 가져옴
				int pointsToAdd = searchCheck.getRecyclePoint(); // Tb_Recycling에서 가져온 포인트 지급량
				// Tb_Point_Earn에 데이터 추가
				Tb_Point_Earn pointEarn = new Tb_Point_Earn();
				pointEarn.setUserId(userEntity);
				pointEarn.setEarnPoint(pointsToAdd);
				pointEarn.setRecycleNum(searchCheck);
				pointEarn.setEarnedAt(earnTime);
				pointEarn.setTownNum(town);
				pointEarnRepo.save(pointEarn);
			}
			Map<String, Object> responseData = new HashMap<>();
			Integer totalEarn = userRepo.calculateTotalPoints(userEntity.getUserId());
			Integer totalUse = hisRepo.usePoint(userEntity.getUserId());
			if(totalEarn!= null && totalUse != null) {
				Integer totalPoints = totalEarn - totalUse;
				responseData.put("totalPoints", totalPoints);
			}else if (totalEarn!=null && totalUse == null) {
				responseData.put("totalPoints", totalEarn);
			} else {
				responseData.put("totalPoints", 0);
			}
			responseData.put("searchCheck", searchCheck);
			ObjectMapper objectMapper = new ObjectMapper();
			System.out.println("????");
			try {
				System.out.println(
						"333333===============================================================================================");
				String Data = objectMapper.writeValueAsString(responseData);
				System.out.println("?????");
				System.out.println(Data);
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

	private Tb_Town findTownByUserAddress(String userAddr) {

		// 유저 주소 파싱해서 동네 정보 추출
		String[] addressParts = userAddr.split(" ");
		System.out.println(userAddr);
		// 동네 정보가 있는지 확인
		if (addressParts.length >= 2) {
			String townName = addressParts[2]; // 3번째 단어를 동네로 간주
			System.out.println(townName);
			// 동네 이름으로 Tb_Town을 찾아 반환
			return townRepo.findByTownName(townName);
		} else {
			// 동네 정보가 없으면 null 반환 또는 적절한 로직 추가
			return null;
		}

	}

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
				Tb_Town town = findTownByUserAddress(userEntity.getUserAddr()); // 유저 주소에 해당하는 타운넘버 가져옴
				int pointsToAdd = recyData.getRecyclePoint(); // Tb_Recycling에서 가져온 포인트 지급량
				System.out.println(pointsToAdd
						+ "=======================================================================================");
				// Tb_Point_Earn에 데이터 추가
				Tb_Point_Earn pointEarn = new Tb_Point_Earn();
				pointEarn.setUserId(userEntity);
				pointEarn.setEarnPoint(pointsToAdd);
				pointEarn.setRecycleNum(recyData);
				pointEarn.setEarnedAt(earnTime);
				pointEarn.setTownNum(town);
				pointEarnRepo.save(pointEarn);
			}
			Map<String, Object> responseData = new HashMap<>();
			Integer totalEarn = userRepo.calculateTotalPoints(userEntity.getUserId());
			Integer totalUse = hisRepo.usePoint(userEntity.getUserId());
			if(totalEarn!= null && totalUse != null) {
				Integer totalPoints = totalEarn - totalUse;
				responseData.put("totalPoints", totalPoints);
			}else if (totalEarn!=null && totalUse == null) {
				responseData.put("totalPoints", totalEarn);
			} else {
				responseData.put("totalPoints", 0);
			}
			responseData.put("recyData", recyData);
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

	@PostMapping("/cateRecySend")
	public String cateRecySend(String clickedTitle, String method) {

		Tb_Recycling cateRecySend = searchRepo.findByTrashNameAndSearchMethod(clickedTitle, method);

		if (cateRecySend != null) {
			int viewNum = cateRecySend.getRecycleViews();
			viewNum += 1;
			cateRecySend.setRecycleViews(viewNum);
			searchRepo.save(cateRecySend);

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonCheck;
			try {
				jsonCheck = objectMapper.writeValueAsString(cateRecySend);
				return jsonCheck;
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "여기가 문제2";
			}

		}

		return "???";
	}

	// 인기검색어 보내주기
	@PostMapping("/popData")
	public String popData() {
		// trash_name으로 그룹화하여 recycle_views를 합친 결과를 가져오기
		List<Object[]> result = searchRepo.sumRecycleViewsByTrashName();

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String jsonResult = objectMapper.writeValueAsString(result);
			return jsonResult;
		} catch (JsonProcessingException e) {
			// 변환 중 오류가 발생한 경우 예외 처리
			e.printStackTrace();
			return "Error during JSON conversion";
		}

	}

	@PostMapping("/popRecy")
	public String popRecy(String data, String method) {

		Tb_Recycling popRecy = searchRepo.findByTrashNameAndSearchMethod(data, method);
		System.out.println(popRecy + "===================================================================");
		if (popRecy != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				String popRecyData = objectMapper.writeValueAsString(popRecy);
				System.out.println(popRecyData
						+ "============================================================================================");
				return popRecyData;
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "뭐임";
	}

	@PostMapping("/upPop")
	public String upPop(String method) {

		List<Tb_Recycling> upPop = searchRepo.findBySearchMethod(method);

		Map<String, Object> responseData = new HashMap<>();

		String base64_upPop = null;

		for (int i = 0; i < upPop.size(); i++) {
			Tb_Recycling recycling = upPop.get(i);
			String recycleImg = recycling.getRecycleImg();
			try {

				byte[] popRecyImg = Files.readAllBytes(Paths.get(recycleImg));

				base64_upPop = Base64.getEncoder().encodeToString(popRecyImg);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// recycleImg 값이 비어있지 않다면 JSON에 추가
			if (recycleImg != null && !recycleImg.isEmpty()) {
				String key = "image" + i;
				responseData.put(key, base64_upPop);
			}
		}

		ObjectMapper objectMapper = new ObjectMapper();

		String jsonCheck;
		try {
			jsonCheck = objectMapper.writeValueAsString(responseData);
			return jsonCheck;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "여기가 문제2";
		}

	}

}
