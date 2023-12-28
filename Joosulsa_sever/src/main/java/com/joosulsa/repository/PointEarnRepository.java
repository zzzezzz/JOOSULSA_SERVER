package com.joosulsa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_Point_Earn;

@Repository
public interface PointEarnRepository extends JpaRepository<Tb_Point_Earn, String> {

}
