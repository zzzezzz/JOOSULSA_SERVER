package com.joosulsa.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class Tb_Town {
	
	// 동네 번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "town_num")
	private Long townNum;
	
	// 동네 이름
	@Column(name = "town_name", length = 500)
	private String townName;
	
	// 동네 x좌표
	@Column(name = "town_x", length = 50)
	private String townX;
	
	// 동네 y좌표
	@Column(name = "town_y", length = 50)
	private String townY;
	
	@OneToMany(mappedBy = "townNum")
	@JsonManagedReference
    private List<Tb_Point_Earn> earnNum;
    
    @Override
	public String toString() {
		return "Tb_Town";
	}
	
	
	
	
}
