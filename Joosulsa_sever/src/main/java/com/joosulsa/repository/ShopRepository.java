package com.joosulsa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_Product;

@Repository
public interface ShopRepository extends JpaRepository<Tb_Product, String>{	 

}
