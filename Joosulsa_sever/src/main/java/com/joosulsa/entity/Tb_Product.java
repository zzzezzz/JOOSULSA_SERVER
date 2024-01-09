package com.joosulsa.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_product")
public class Tb_Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prod_num")
	private Long prodNum;

	@Column(name = "prod_name", length = 50, nullable = false)
	private String prodName;

	@Column(name = "prod_price", nullable = false)
	private Integer prodPrice;

	@Column(name = "prod_info", columnDefinition = "TEXT", nullable = false)
	private String prodInfo;

	@Column(name = "prod_img", columnDefinition = "TEXT", nullable = false)
	private String prodImg;
	
	@OneToMany(mappedBy = "prodNum", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Tb_Point_History> histNum;
	
	@Override
    public String toString() {
    	return "Tb_Product";
    }
	
}
