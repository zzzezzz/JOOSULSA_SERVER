package com.joosulsa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_Town;

@Repository
public interface TownRepository extends JpaRepository<Tb_Town, String>{

	Tb_Town findByTownName(String townName);

}
