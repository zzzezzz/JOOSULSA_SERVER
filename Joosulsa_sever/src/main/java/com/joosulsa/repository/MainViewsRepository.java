package com.joosulsa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.joosulsa.entity.Tb_Recycling;

public interface MainViewsRepository extends JpaRepository<Tb_Recycling, String>{
		
	
	// 메인 페이지의 상단에 조회수대로 쓰레기
	@Query("SELECT r.trashName FROM Tb_Recycling r GROUP BY r.trashName ORDER BY SUM(r.recycleViews) DESC")
	List<String> findTrashNamesOrderedByTotalRecycleViewsDesc();
	
	
}
