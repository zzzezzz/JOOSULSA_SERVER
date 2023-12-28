package com.joosulsa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joosulsa.entity.Tb_Quiz;
import com.joosulsa.entity.Tb_User;

@Repository
public interface QuizRepository extends JpaRepository<Tb_Quiz, String> {

	Tb_Quiz findByQuizNum(long longQuiz);


}
