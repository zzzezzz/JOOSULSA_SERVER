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
@Table(name = "tb_recycling")
public class Tb_Recycling {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recycle_num")
    private Long recycleNum;

    @Column(name = "trash_name", length = 50, nullable = false)
    private String trashName;

    @Column(name = "sepa_method", columnDefinition = "TEXT", nullable = false)
    private String sepaMethod;

    @Column(name = "sepa_video", length = 1000, nullable = false)
    private String sepaVideo;

    @Column(name = "recycle_views", nullable = false)
    private Integer recycleViews;

    @Column(name = "recycle_video", length = 1000, nullable = false)
    private String recycleVideo;

    @Column(name = "recycle_method", columnDefinition = "TEXT", nullable = false)
    private String recycleMethod;

    @Column(name = "recycled_at", nullable = false)
    private LocalDateTime recycledAt;

    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id")
    private Tb_User userId;

    @Column(name = "recycle_point", nullable = false)
    private Integer recyclePoint;

    @Column(name = "search_method", length = 20, nullable = false)
    private String searchMethod;
	
}
