package com.joosulsa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_Town;

@Repository
public interface TownRepository extends JpaRepository<Tb_Town, String>{
	
	
	@Query("SELECT SUM(te.earnPoint) FROM Tb_Point_Earn te WHERE te.townNum = :townNum")
    Integer calculateTotalPointsByTownNum(@Param("townNum") String townNum);
    
	


}
