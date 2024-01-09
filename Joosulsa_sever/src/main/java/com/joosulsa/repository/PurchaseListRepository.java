package com.joosulsa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_Point_History;

@Repository
public interface PurchaseListRepository extends JpaRepository<Tb_Point_History, String>{

	// 유저 아이디 기준으로 가져오겠다
	// 리턴타입과 동일하게 메서드 선언.
	@Query("")
	public List<Tb_Point_History> findAllByUserId(String userId);
}
