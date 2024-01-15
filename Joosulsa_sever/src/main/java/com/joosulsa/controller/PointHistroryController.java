package com.joosulsa.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joosulsa.entity.Tb_Point_History;
import com.joosulsa.entity.Tb_Product;
import com.joosulsa.entity.Tb_User;
import com.joosulsa.repository.PointHistroryRepository;
import com.joosulsa.repository.ShopRepository;
import com.joosulsa.repository.UserRepository;

@RestController
@Transactional
public class PointHistroryController {

	@Autowired
	private PointHistroryRepository poRepo;
	
	@Autowired
	private ShopRepository shopRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	// 
	
	@PostMapping("/purchaseProduct")
	public String purchaseProduct(String use_point, String used_at, String user_id_user_id, String prod_name) {
		System.out.println("check : "+use_point+used_at+user_id_user_id+prod_name);
		Tb_Point_History pointHistory = new Tb_Point_History();
		
		Tb_Product searchNum = shopRepo.findByProdName(prod_name);
		Tb_User user = userRepo.findByUserId(user_id_user_id);
		
		pointHistory.setProdNum(searchNum);
		pointHistory.setUsedAt(used_at);
		pointHistory.setUsePoint(Integer.parseInt(use_point));
		pointHistory.setUserId(user);
		
		poRepo.save(pointHistory);
		
		int earnPoint = userRepo.calculateTotalPoints(user_id_user_id);
		Integer usePoint = poRepo.usePoint(user_id_user_id);
		
		String totalPoint = String.valueOf(earnPoint - usePoint);
		
		return totalPoint;
	}
}
