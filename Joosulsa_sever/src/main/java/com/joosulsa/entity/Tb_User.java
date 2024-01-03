package com.joosulsa.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
public class Tb_User {
	
	@Id
    @Column(name = "user_id", length = 30, nullable = false)
    private String userId;

    @Column(name = "user_pw", length = 40, nullable = false)
    private String userPw;

    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    @Column(name = "user_addr", length = 1000, nullable = false)
    private String userAddr;

    @Column(name = "user_nick", length = 30, nullable = false)
    private String userNick;
    
    @Column(name = "today_att")
    private boolean attendance = false;
    
    @Column(name = "quiz_att")
    private boolean quizParticipation = false;
    
    @Column(name = "monthly_attendance")
    private int monthlyAttendance = 0;

    @Column(name = "joined_at", nullable = false)
    private String joinedAt;
    
    @OneToMany(mappedBy = "userId")
    private List<Tb_Point_History> useNum;
    
    @OneToMany(mappedBy = "userId")
    @JsonManagedReference
    private List<Tb_Point_Earn> earnNum;

	public boolean getAttendance;
    
    @Override
    public String toString() {
    	return "Tb_User";
    }
	
}
