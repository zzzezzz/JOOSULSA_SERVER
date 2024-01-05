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
@Table(name = "tb_recycling")
public class Tb_Recycling {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recycle_num")
    private Long recycleNum;

    @Column(name = "trash_name", length = 50)
    private String trashName;

    @Column(name = "sepa_method", columnDefinition = "TEXT")
    private String sepaMethod;
    
    @Column(name = "sepa_caution", columnDefinition = "TEXT")
    private String sepaCaution;
    
    @Column(name = "sepa_img", length = 1000)
    private String sepaImg;

    @Column(name = "sepa_video", length = 1000)
    private String sepaVideo;

    @Column(name = "recycle_views")
    private Integer recycleViews;

    @Column(name = "recycle_video", length = 1000)
    private String recycleVideo;

    @Column(name = "recycle_img", length = 1000)
    private String recycleImg;
    
    @Column(name = "recycle_point")
    private Integer recyclePoint;

    @Column(name = "search_method", length = 20)
    private String searchMethod;
    
    @OneToMany(mappedBy = "recycleNum")
    @JsonBackReference
    private List<Tb_Point_Earn> earnNum;
	
    @Override
	public String toString() {
		return "Tb_Recycle";
	}
}
