package com.joosulsa.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.joosulsa.entity.Tb_Point_History;
import com.joosulsa.entity.Tb_Product;

public interface PointHistroryRepository extends JpaRepository<Tb_Point_History, String>{
	
	@Query("SELECT SUM(usePoint) FROM Tb_Point_History WHERE userId.userId = :user_id_user_id")
	int usePoint(@Param("user_id_user_id") String user_id_user_id);
	
	
}
