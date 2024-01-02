package com.joosulsa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joosulsa.entity.Tb_Attendance;

public interface CheckRepository extends JpaRepository<Tb_Attendance, String>{
	
	
	
}
