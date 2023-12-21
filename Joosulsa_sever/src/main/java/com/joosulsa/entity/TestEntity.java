package com.joosulsa.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO 파일
@Entity // 테이블과 1:1로 매칭해주기
@Data // 롱북 불러오기
@AllArgsConstructor // 생성자 만들주는 어노테이션
@NoArgsConstructor // 기본생성자 만들주는 어노테이션
public class TestEntity {
	private String test2;
}
