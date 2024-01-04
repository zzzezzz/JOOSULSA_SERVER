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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_point_history")
public class Tb_Point_History {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "use_num")
    private Long useNum;

    @Column(name = "use_point", nullable = false)
    private Integer usePoint;
   
    @ManyToOne
    @JoinColumn(name="prod_num", nullable = false)
    @JsonBackReference
    private Tb_Product prodNum;

    @Column(name = "used_at", nullable = false)
    private String usedAt;

    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id")
    @JsonBackReference
    private Tb_User userId;
	
}
