package com.joosulsa.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_Recycling;

@Repository
public interface SearchRepository extends JpaRepository<Tb_Recycling, String> {

	// 이름 기준으로 가져오겠다.
	public Tb_Recycling findByTrashName(String search);

	// SearchMethod로 묶어주는 이유 : 비회원이 검색시 포인트 적립처리 안하게 하려고…
	public Tb_Recycling findByTrashNameAndSearchMethod(String search, String method);

	@Query("SELECT trashName FROM Tb_Recycling")
	List<String> findTrashNames();

	public Tb_Recycling findByRecycleNum(long longNum);

	public Tb_Recycling findByRecycleNumAndSearchMethod(Long photoNum, String method);

	@Query("SELECT r.trashName, SUM(r.recycleViews) as totalRecycleViews " + "FROM Tb_Recycling r "
			+ "GROUP BY r.trashName " + "ORDER BY totalRecycleViews DESC")
	List<Object[]> sumRecycleViewsByTrashName();

}
