package com.joosulsa.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joosulsa.entity.Tb_Product;
import com.joosulsa.repository.ShopRepository;

@Controller
@Transactional
public class ShopController {
	@Autowired
	private ShopRepository shopRepo;

	// 상품 정보 가져오기
	@PostMapping("/shop")
	@ResponseBody
	public List<Tb_Product> product() {
		List<Tb_Product> result = shopRepo.findAll();
		System.out.println("상품 정보 " + result);
//		ObjectMapper objectMapper = new ObjectMapper();
//		for(int i =0; i<result.size(); i++) {
//			try {
//				String Data = objectMapper.writeValueAsString(result);
//				return Data;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return "실패";
//			}
//			
//		}

		return result;
	}

	// 상품 이미지 가져오기
	@PostMapping("/shopImg")
	@ResponseBody
	public ResponseEntity<List<String>> productImg() {
	    List<Tb_Product> result = shopRepo.findAll();
	    List<String> shopList = new ArrayList<>();

	    for (Tb_Product product : result) {
	        try {
	            byte[] imageBytes = Files.readAllBytes(Paths.get(product.getProdImg()));
	            String base64_img = Base64.getEncoder().encodeToString(imageBytes);
	            System.out.println("이미지 정보: " + base64_img.length());

	            shopList.add(base64_img);
	        } catch (IOException e) {
	            e.printStackTrace();
	            // 예외 발생 시 빈 이미지를 추가하거나 다른 처리를 할 수 있습니다.
	            shopList.add(""); // 빈 이미지 추가
	        }
	    }
	    
	    // ResponseEntity를 사용하여 HTTP 상태코드와 함께 응답
	    return new ResponseEntity<>(shopList, HttpStatus.OK);
	}
}
