package com.joosulsa.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_Point_Earn;

@Repository
public interface PointEarnRepository extends JpaRepository<Tb_Point_Earn, String> {

	@Query("SELECT pe.userId.userId, SUM(pe.earnPoint) as totalPoints " + "FROM Tb_Point_Earn pe "
			+ "GROUP BY pe.userId.userId " + "ORDER BY totalPoints DESC ")
	List<Object[]> findTop10UsersTotalPoints();

	@Query("SELECT COUNT(distinct pe.userId) FROM Tb_Point_Earn pe WHERE "
			+ "(SELECT SUM(pe2.earnPoint) FROM Tb_Point_Earn pe2 WHERE pe2.userId = pe.userId) > "
			+ "(SELECT SUM(pe3.earnPoint) FROM Tb_Point_Earn pe3 WHERE pe3.userId.userId = :userId)")
	Integer findUserRankByTotalPoints(@Param("userId") String userId);

}
