package com.joosulsa.repository;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_User;


@Repository
public interface UserRepository extends JpaRepository<Tb_User, String> {

	public Tb_User findByUserIdAndUserPw(String id, String pw);

	public Tb_User findByUserId(String userId);
	
	// 회원정보 수정 메소드
	@Modifying // select 문이 아님을 나타낸다
	@Query("UPDATE com.joosulsa.entity.Tb_User m SET m.userPw = :newPw, m.userNick = :newNick, m.userAddr = :newAddr WHERE m.userId = :id")
	 int myChange(@Param("id") String id, @Param("newPw") String newPw,
			 @Param("newNick") String newNick, @Param("newAddr") String newAddr);


//	public void markAttendance(String userId);
	
	@Query("SELECT SUM(pe.earnPoint) FROM Tb_Point_Earn pe WHERE pe.userId.userId = :userId")
    int calculateTotalPoints(@Param("userId") String userId);
	
}


