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
import com.joosulsa.dto.ShopDTO;
import com.joosulsa.dto.ShopDTO.shopDTO;
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
	public String product() {
	    System.out.println("??==============================================================================");
	    List<Tb_Product> result = shopRepo.findAll();

	    List<ShopDTO.shopDTO> shopList = new ArrayList<>();
	    
	    String base64_prod = null;
	    
	    if(result!=null) {
	    	
	    	Map<String, Object> responseData = new HashMap<>();
	    	
	    	for (int i = 0; i < result.size(); i++) {
	    		// Tb_Product에서 정보를 가져와서 ShopDTO.shopDTO 객체를 생성합니다.
	    		Tb_Product tbProduct = result.get(i);
	    		ShopDTO.shopDTO shopDTO = new ShopDTO.shopDTO();
	    		shopDTO.setProdName(tbProduct.getProdName());
	    		shopDTO.setProdPrice(tbProduct.getProdPrice());
	    		shopDTO.setProdInfo(tbProduct.getProdInfo());
	    		try {
					String prodImgPath = result.get(i).getProdImg();
					System.out.println(prodImgPath);

					byte[] prodImg = Files.readAllBytes(Paths.get(prodImgPath));

					base64_prod = Base64.getEncoder().encodeToString(prodImg);
					System.out.println("길이 : " + base64_prod.length());

					shopDTO.setProdImg(base64_prod);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "반복문이 안된다";
				}

	    		// ShopDTO 객체를 리스트에 추가합니다.
	    		shopList.add(shopDTO);
	    	}
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	String jsonCheck;
			try {
				jsonCheck = objectMapper.writeValueAsString(shopList);
				return jsonCheck;
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "여기가 문제2";
			}
	    }
		return "우째서?===============================================";
	}

	

}