package com.joosulsa.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_User;


@Repository
public interface UserRepository extends JpaRepository<Tb_User, String> {

	public Tb_User findByUserIdAndUserPw(String id, String pw);

	public Tb_User findByUserId(String userId);
	
	
	
	
	
	
}
