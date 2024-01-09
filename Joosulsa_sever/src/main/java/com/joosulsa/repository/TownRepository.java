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
    
	// 상위 3개의 동네의 포인트 값만 가져오는 쿼리
	@Query("SELECT tt.townName, SUM(te.earnPoint) " +
		       "FROM Tb_Point_Earn te " +
		       "JOIN te.townNum tt " +
		       "GROUP BY te.townNum.townNum, tt.townName " +
		       "ORDER BY SUM(te.earnPoint) DESC")
	List<Object[]> findSumOfEarnPointGroupByTownName();
	
	
	// 사용자가 속한 동네의 포인트를 가지고 오는 쿼리
	@Query("SELECT SUM(te.earnPoint) " +
		       "FROM Tb_Point_Earn te " +
		       "WHERE te.userId.userId = :userId " +
		       "GROUP BY te.townNum.townNum")
	String getTotalEarnPointByUserIds(@Param("userId") String userId);
	// 안드로이드 스튜디오에서 정의한 사용자 아이디를 가지고와서 param에 넣어준다.
	
	// 사용자가 속한 동네의 좌표값을 가지고 오는 쿼리
	@Query("SELECT tt.townX, tt.townY " +
		       "FROM Tb_Town tt " +
		       "JOIN Tb_Point_Earn te ON tt.townNum = te.townNum.townNum " +
		       "WHERE te.userId.userId = :userId ")
	List<Object[]> getTownCoordinatesByUserId(@Param("userId") String userId);
	

}
