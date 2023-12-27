package com.joosulsa.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_quiz")
public class Tb_Quiz {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_num")
    private Long quizNum;

    @Column(name = "quiz_content", columnDefinition = "TEXT", nullable = false)
    private String quizContent;

    @Column(name = "quiz_answer", columnDefinition = "TEXT", nullable = false)
    private String quizAnswer;

    @Column(name = "quiz_info", columnDefinition = "TEXT", nullable = true)
    private String quizInfo;
    
    @Column(name = "quiz_point", nullable = false)
    private Integer quizPoint;
    
    @OneToMany(mappedBy = "quizNum")
    private List<Tb_Point_Earn> earnNum;
    
    @Override
	public String toString() {
		return "Tb_Quiz";
	}
	
}
