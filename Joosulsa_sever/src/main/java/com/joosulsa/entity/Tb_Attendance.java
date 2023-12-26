package com.joosulsa.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_attendance")
public class Tb_Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "att_num")
	private Long attNum;

	@Column(name = "attended_at", nullable = false)
	private LocalDateTime attendedAt;

	@Column(name = "att_point", nullable = false)
	private Integer attPoint;

	@ManyToOne
	@JoinColumn(referencedColumnName = "user_id")
	private Tb_User userId;

}
