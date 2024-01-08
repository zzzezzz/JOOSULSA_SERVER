package com.joosulsa.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_Town;

@Repository
public interface TownRepository extends JpaRepository<Tb_Town, String>{
	
	Tb_Town findByTownName(String townName);
	
	@Query("SELECT SUM(te.earnPoint) FROM Tb_Point_Earn te WHERE te.townNum.townNum = :townNum")
    String calculateTotalPointsByTownNum(@Param("townNum") Long townNum);
    
	// 상위 3개의 동네의 포인트 값만 가져오는 메소드
	@Query("SELECT SUM(te.earnPoint), tt.townName " +
		       "FROM Tb_Point_Earn te " +
		       "JOIN te.townNum tt " +
		       "GROUP BY te.townNum.townNum, tt.townName " +
		       "ORDER BY SUM(te.earnPoint) DESC")
		List<Object[]> findSumOfEarnPointGroupByTownName();


}
