package com.joosulsa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_Point_Earn;

@Repository
public interface PointEarnRepository extends JpaRepository<Tb_Point_Earn, String> {
	
	@Query("SELECT pe.userId.userId, SUM(pe.earnPoint) as totalPoints " +
	           "FROM Tb_Point_Earn pe " +
	           "GROUP BY pe.userId.userId " +
	           "ORDER BY totalPoints DESC ")
	    List<Object[]> findTop10UsersTotalPoints();
	    
	   
	
	
	
}
