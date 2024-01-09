package com.joosulsa.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joosulsa.entity.Tb_Point_History;
import com.joosulsa.repository.PurchaseListRepository;



@Controller
@Transactional
public class PurchaseList {
	
	@Autowired
	private PurchaseListRepository listRepo;
	
	// 구매내역 조회
	@PostMapping("/purchList")
	@ResponseBody
	public List<Tb_Point_History> polist(String userId){
		List<Tb_Point_History> resulst = listRepo.findAllByUserId(userId);
		
		return resulst;
	}

}
