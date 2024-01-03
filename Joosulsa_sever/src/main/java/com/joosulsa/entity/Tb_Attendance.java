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

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

	@Column(name = "attended_at", nullable = true)
	private String attendedAt;

	@Column(name = "att_point", nullable = false)
	private Integer attPoint;

	@OneToMany(mappedBy = "attNum")
	@JsonManagedReference
    private List<Tb_Point_Earn> earnNum;
    
    @Override
	public String toString() {
		return "Tb_Attendance";
	}

}
