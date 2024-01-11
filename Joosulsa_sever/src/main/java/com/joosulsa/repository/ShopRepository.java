package com.joosulsa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_Product;

@Repository
public interface ShopRepository extends JpaRepository<Tb_Product, String>{	 
	

	
	Tb_Product findByProdName(String prodName);
}
