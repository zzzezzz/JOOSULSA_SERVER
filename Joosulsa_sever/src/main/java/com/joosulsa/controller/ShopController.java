package com.joosulsa.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joosulsa.entity.Tb_Product;
import com.joosulsa.repository.ShopRepository;

@RestController
@Transactional
public class ShopController {

	@Autowired
	private ShopRepository shopRepo;

	// 상품 정보 가져오기
	@PostMapping("/shop")
	@ResponseBody
	public List<Tb_Product> product() {
		List<Tb_Product> result = shopRepo.findAll();

		return result;
	}

	// 상품 이미지 가져오기
	@PostMapping("/shopImg")
	public String shopImg() {

		List<Tb_Product> imgRes = shopRepo.findAll();

		String base64_prod = null;

		if (imgRes != null) {
			Map<String, Object> responseData = new HashMap<>();
			for (int i = 0; i < imgRes.size(); i++) {
				try {
					String prodImgPath = imgRes.get(i).getProdImg();
					System.out.println(prodImgPath);
					
					byte[] prodImg = Files.readAllBytes(Paths.get(prodImgPath));
					
					base64_prod = Base64.getEncoder().encodeToString(prodImg);
					System.out.println("길이 : " + base64_prod.length());
					
					responseData.put("prodImg" + i, base64_prod);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "반복문이 안된다";
				}
			}
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
		}

		return "몰?루";
	}

}